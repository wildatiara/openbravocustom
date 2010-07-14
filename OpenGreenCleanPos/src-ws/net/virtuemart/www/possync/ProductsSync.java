//    GreenPOS is a point of sales application designed for touch screens.
//    http://code.google.com/p/openbravocustom/
//    Copyright (c) 2007 openTrends Solucions i Sistemes, S.L
//    Modified by Openbravo SL on March 22, 2007
//    These modifications are copyright Openbravo SL
//    Author/s: A. Romero
//    You may contact Openbravo SL at: http://www.openbravo.com
//
//    This file is part of GreenPOS.
//
//    GreenPOS is free software: you can redistribute it and/or modify
//    it under the terms of the GNU General Public License as published by
//    the Free Software Foundation, either version 3 of the License, or
//    (at your option) any later version.
//
//    GreenPOS is distributed in the hope that it will be useful,
//    but WITHOUT ANY WARRANTY; without even the implied warranty of
//    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//    GNU General Public License for more details.
//
//    You should have received a copy of the GNU General Public License
//    along with GreenPOS.  If not, see <http://www.gnu.org/licenses/>.

package net.virtuemart.www.possync;

import java.net.MalformedURLException;
import java.rmi.RemoteException;
import javax.xml.rpc.ServiceException;

import org.apache.commons.beanutils.locale.converters.DateLocaleConverter;
import org.hsqldb.lib.MD5;

import net.virtuemart.www.VM_Users.AddUserInput;
import net.virtuemart.www.VM_Users.User;
import net.virtuemart.www.customers.Customer;
import net.virtuemart.www.externalsales.Product;
import net.virtuemart.www.externalsales.ProductPlus;

import com.openbravo.basic.BasicException;
import com.openbravo.data.gui.MessageInf;
import com.openbravo.data.loader.ImageUtils;
import com.openbravo.pos.customers.CustomerSync;
import com.openbravo.pos.forms.AppLocal;
import com.openbravo.pos.forms.DataLogicSales;
import com.openbravo.pos.forms.DataLogicSystem;
import com.openbravo.pos.forms.ProcessAction;
import com.openbravo.pos.inventory.MovementReason;
import com.openbravo.pos.inventory.TaxCategoryInfo;
import com.openbravo.pos.ticket.CategoryInfo;
import com.openbravo.pos.ticket.ProductInfoExt;
import com.openbravo.pos.ticket.TaxInfo;
import net.virtuemart.www.possync.DataLogicIntegration;
import net.virtuemart.www.possync.ExternalSalesHelper;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class ProductsSync implements ProcessAction {
        
    private DataLogicSystem dlsystem;
    private DataLogicIntegration dlintegration;
    private DataLogicSales dlsales;
    private String warehouse;
    private ExternalSalesHelper externalsales;
    
    /** Creates a new instance of ProductsSync */
    public ProductsSync(DataLogicSystem dlsystem, DataLogicIntegration dlintegration, DataLogicSales dlsales,  String warehouse) {
        this.dlsystem = dlsystem;
        this.dlintegration = dlintegration;
        this.dlsales = dlsales;
        this.warehouse = warehouse;
        externalsales = null;
    }
    
    public MessageInf execute() throws BasicException {
       
        try {
        
            if (externalsales == null) {
                externalsales = new ExternalSalesHelper(dlsystem);
            }
            
            Product[] products = externalsales.getProductsCatalog();

            if (products == null) {
                throw new BasicException(AppLocal.getIntString("message.returnnull")+" > Products null");
            }
  
            
            if (false && products.length > 0){
                
                dlintegration.syncProductsBefore();
                
                Date now = new Date();
                
                for (Product product : products) {
                    
                    // Synchonization of taxcategories
                    TaxCategoryInfo tc = new TaxCategoryInfo(product.getTax().getId(), product.getTax().getName());
                    dlintegration.syncTaxCategory(tc);
                    
                    // Synchonization of taxes
                    TaxInfo t = new TaxInfo(
                            product.getTax().getId(),
                            product.getTax().getName(),
                            tc.getID(),
                            null,
                            null,
                            product.getTax().getPercentage() / 100,
                            false,
                            0);
                    dlintegration.syncTax(t);
                   
                    // Synchonization of categories
                    CategoryInfo c = new CategoryInfo(product.getCategory().getId(), product.getCategory().getName(), null);
                    dlintegration.syncCategory(c);

                    // Synchonization of products
                    ProductInfoExt p = new ProductInfoExt();
                    p.setID(product.getId());
                    p.setReference(product.getId());
                    p.setCode(product.getEan() == null || product.getEan().equals("") ? product.getId() : product.getEan());
                    p.setName(product.getName());
                    p.setCom(false);
                    p.setScale(false);
                    p.setPriceBuy(product.getPurchasePrice());
                    p.setPriceSell(product.getListPrice());
                    p.setCategoryID(c.getID());
                    p.setTaxCategoryID(tc.getID());
                    p.setImage(ImageUtils.readImage(product.getImageUrl()));
                    dlintegration.syncProduct(p);  
                    
                    // Synchronization of stock          
                    if (product instanceof ProductPlus) {
                        
                        ProductPlus productplus = (ProductPlus) product;
                        
                        double diff = productplus.getQtyonhand() - dlsales.findProductStock(warehouse, p.getID(), null);
                        
                        Object[] diary = new Object[7];
                        diary[0] = UUID.randomUUID().toString();
                        diary[1] = now;
                        diary[2] = diff > 0.0 
                                ? MovementReason.IN_MOVEMENT.getKey()
                                : MovementReason.OUT_MOVEMENT.getKey();
                        diary[3] = warehouse;
                        diary[4] = p.getID();
                        diary[5] = new Double(diff);
                        diary[6] = new Double(p.getPriceBuy());                                
                        dlsales.getStockDiaryInsert().exec(diary);   
                    }
                }
                
                // datalogic.syncProductsAfter();
            }
 
            Customer[] customers = externalsales.getCustomers();

            if (customers == null){
                throw new BasicException(AppLocal.getIntString("message.returnnull")+" > Customers null");
            }
            
            if (customers.length > 0 ) {
                
//                dlintegration. syncCustomersBefore();
//                for (Customer customer : customers) {                    
//                    CustomerInfoExt cinfo = new CustomerInfoExt(customer.getId());
//                    cinfo.setSearchkey(customer.getSearchKey());
//                    cinfo.setName(customer.getName());          
//                    cinfo.setNotes(customer.getDescription());
//                    // TODO: Finish the integration of all fields.
//                    dlintegration.syncCustomer(cinfo);
//                }
            }
            //List<TicketInfo> clist = dlintegration.getTicketsTest();
            List<CustomerSync> clist = dlintegration.getCustomers();
            System.out.println(" >> "+clist.size()+ "  " + clist.toString());
			
            for (CustomerSync cInfo : clist) {
            	//System.out.println(" >> "+cInfo.getTaxid());
            	
                User userAdd = new User();
    			userAdd.setLogin(cInfo.getTaxid());
    			userAdd.setId(cInfo.getTaxid());
    			userAdd.setFirstname(" ");
    			userAdd.setLastname(cInfo.getName());
    			userAdd.setPassword("407b3273beea2c061dbe7fc11b68de43");
    			userAdd.setTitle("Mr");
    			if (cInfo.getEmail()==null || cInfo.getEmail().indexOf('@')==-1)
    				userAdd.setEmail(cInfo.getTaxid()+"@greenandclean.be");
    			else
    				userAdd.setEmail(" "+cInfo.getEmail());
    			userAdd.setDescription(" "+cInfo.getNotes());
    			userAdd.setAddress(" "+cInfo.getAddress());
    			userAdd.setAddress2(" "+cInfo.getAddress2());

    			userAdd.setState_region(" "+cInfo.getRegion());
    			userAdd.setCity(" "+cInfo.getCity());
    			userAdd.setCountry(" "+cInfo.getCountry());
    			userAdd.setZipcode(" "+cInfo.getPostal());
    			userAdd.setPhone(" "+cInfo.getPhone());
    			userAdd.setMobile(" "+cInfo.getPhone2());
    			userAdd.setFax(" ");
    		
    			System.out.println("* "+userAdd.toString());
    			
    			externalsales.addCustomer(userAdd);
			}
            
            

            
            if (products.length == 0 && customers.length == 0) {
                return new MessageInf(MessageInf.SGN_NOTICE, AppLocal.getIntString("message.zeroproducts"));               
            } else {
                return new MessageInf(MessageInf.SGN_SUCCESS, AppLocal.getIntString("message.syncproductsok"), AppLocal.getIntString("message.syncproductsinfo", products.length, customers.length));
            }
                
        } catch (ServiceException e) {            
            throw new BasicException(AppLocal.getIntString("message.serviceexception"), e);
        } catch (RemoteException e){
            throw new BasicException(AppLocal.getIntString("message.remoteexception"), e);
        } catch (MalformedURLException e){
            throw new BasicException(AppLocal.getIntString("message.malformedurlexception"), e);
        }
    }   
}