/*
 * @(#)tsUUID.java	1.18 06/06/02
 *
 * Copyright 2006 Sun Microsystems, Inc. All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.openbravo.pos.util;

import java.security.*;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * A class that represents an immutable universally unique identifier (tsUUID).
 * A tsUUID represents a 128-bit value.
 *
 * <p>There exist different variants of these global identifiers. The methods
 * of this class are for manipulating the Leach-Salz variant, although the
 * constructors allow the creation of any variant of tsUUID (described below).
 *
 * <p>The layout of a variant 2 (Leach-Salz) tsUUID is as follows:
 *
 * The most significant long consists of the following unsigned fields:
 * <pre>
 * 0xFFFFFFFF00000000 time_low
 * 0x00000000FFFF0000 time_mid
 * 0x000000000000F000 version
 * 0x0000000000000FFF time_hi
 * </pre>
 * The least significant long consists of the following unsigned fields:
 * <pre>
 * 0xC000000000000000 variant
 * 0x3FFF000000000000 clock_seq
 * 0x0000FFFFFFFFFFFF node
 * </pre>
 *
 * <p>The variant field contains a value which identifies the layout of
 * the <tt>tsUUID</tt>. The bit layout described above is valid only for
 * a <tt>tsUUID</tt> with a variant value of 2, which indicates the
 * Leach-Salz variant.
 *
 * <p>The version field holds a value that describes the type of this
 * <tt>tsUUID</tt>. There are four different basic types of UUIDs: time-based,
 * DCE security, name-based, and randomly generated UUIDs. These types
 * have a version value of 1, 2, 3 and 4, respectively.
 *
 * <p>For more information including algorithms used to create <tt>tsUUID</tt>s,
 * see <a href="http://www.ietf.org/rfc/rfc4122.txt">
 * <i>RFC&nbsp;4122: A Universally Unique IDentifier (tsUUID) URN
 * Namespace</i></a>, section 4.2 &quot;Algorithms for Creating a Time-Based
 * tsUUID&quot;.
 *
 * @version 1.18, 06/02/06
 * @since   1.5
 */
public final class tsUUID
implements java.io.Serializable, Comparable<tsUUID> {

    /**
     * Explicit serialVersionUID for interoperability.
     */
     private static final long serialVersionUID = -4856846361193249489L;

    /*
     * The most significant 64 bits of this tsUUID.
     *
     * @serial
     */
    private final long mostSigBits;

    /*
     * The least significant 64 bits of this tsUUID.
     *
     * @serial
     */
    private final long leastSigBits;

    /*
     * The version number associated with this tsUUID. Computed on demand.
     */
    private transient int version = -1;

    /*
     * The variant number associated with this tsUUID. Computed on demand.
     */
    private transient int variant = -1;

    /*
     * The timestamp associated with this tsUUID. Computed on demand.
     */
    private transient volatile long timestamp = -1;

    /*
     * The clock sequence associated with this tsUUID. Computed on demand.
     */
    private transient int sequence = -1;

    /*
     * The node number associated with this tsUUID. Computed on demand.
     */
    private transient long node = -1;

    /*
     * The hashcode of this tsUUID. Computed on demand.
     */
    private transient int hashCode = -1;

    /*
     * The random number generator used by this class to create random
     * based UUIDs.
     */
    private static volatile SecureRandom numberGenerator = null;

    // Constructors and Factories

    /*
     * Private constructor which uses a byte array to construct the new tsUUID.
     */
    private tsUUID(byte[] data) {
        long msb = 0;
        assert data.length == 16;
        for (int i=8; i<16; i++)
            msb = (msb << 8) | (data[i] & 0xff);
        this.mostSigBits = msb;
        this.leastSigBits = new Date().getTime();
    }

    /**
     * Constructs a new <tt>tsUUID</tt> using the specified data.
     * <tt>mostSigBits</tt> is used for the most significant 64 bits
     * of the <tt>tsUUID</tt> and <tt>leastSigBits</tt> becomes the
     * least significant 64 bits of the <tt>tsUUID</tt>.
     *
     * @param  mostSigBits
     * @param  leastSigBits
     */
    public tsUUID(long mostSigBits, long leastSigBits) {
        this.mostSigBits = mostSigBits;
        this.leastSigBits = leastSigBits;
    }

    /**
     * Static factory to retrieve a type 4 (pseudo randomly generated) tsUUID.
     *
     * The <code>tsUUID</code> is generated using a cryptographically strong
     * pseudo random number generator.
     *
     * @return  a randomly generated <tt>tsUUID</tt>.
     */
    public static tsUUID randomUUID() {
        SecureRandom ng = numberGenerator;
        if (ng == null) {
            numberGenerator = ng = new SecureRandom();
        }

        byte[] randomBytes = new byte[16];
        ng.nextBytes(randomBytes);
        randomBytes[6]  &= 0x0f;  /* clear version        */
        randomBytes[6]  |= 0x40;  /* set to version 4     */
        randomBytes[8]  &= 0x3f;  /* clear variant        */
        randomBytes[8]  |= 0x80;  /* set to IETF variant  */
        return new tsUUID(randomBytes);
    }

    /**
     * Static factory to retrieve a type 3 (name based) <tt>tsUUID</tt> based on
     * the specified byte array.
     *
     * @param  name a byte array to be used to construct a <tt>tsUUID</tt>.
     * @return  a <tt>tsUUID</tt> generated from the specified array.
     */
    public static tsUUID nameUUIDFromBytes(byte[] name) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException nsae) {
            throw new InternalError("MD5 not supported");
        }
        byte[] md5Bytes = md.digest(name);
        md5Bytes[6]  &= 0x0f;  /* clear version        */
        md5Bytes[6]  |= 0x30;  /* set to version 3     */
        md5Bytes[8]  &= 0x3f;  /* clear variant        */
        md5Bytes[8]  |= 0x80;  /* set to IETF variant  */
        return new tsUUID(md5Bytes);
    }

    /**
     * Creates a <tt>tsUUID</tt> from the string standard representation as
     * described in the {@link #toString} method.
     *
     * @param  name a string that specifies a <tt>tsUUID</tt>.
     * @return  a <tt>tsUUID</tt> with the specified value.
     * @throws IllegalArgumentException if name does not conform to the
     *         string representation as described in {@link #toString}.
     */
    public static tsUUID fromString(String name) {
        String[] components = name.split("-");
        if (components.length != 5)
            throw new IllegalArgumentException("Invalid UUID string: "+name);
        for (int i=0; i<5; i++)
            components[i] = "0x"+components[i];

        long mostSigBits = Long.decode(components[0]).longValue();
        mostSigBits <<= 16;
        mostSigBits |= Long.decode(components[1]).longValue();
        mostSigBits <<= 16;
        mostSigBits |= Long.decode(components[2]).longValue();

        long leastSigBits = Long.decode(components[3]).longValue();
        leastSigBits <<= 48;
        leastSigBits |= Long.decode(components[4]).longValue();

        return new tsUUID(mostSigBits, leastSigBits);
    }

    // Field Accessor Methods

    /**
     * Returns the least significant 64 bits of this tsUUID's 128 bit value.
     *
     * @return the least significant 64 bits of this tsUUID's 128 bit value.
     */
    public long getLeastSignificantBits() {
        return leastSigBits;
    }

    /**
     * Returns the most significant 64 bits of this tsUUID's 128 bit value.
     *
     * @return the most significant 64 bits of this tsUUID's 128 bit value.
     */
    public long getMostSignificantBits() {
        return mostSigBits;
    }

    /**
     * The version number associated with this <tt>tsUUID</tt>. The version
     * number describes how this <tt>tsUUID</tt> was generated.
     *
     * The version number has the following meaning:<p>
     * <ul>
     * <li>1    Time-based tsUUID
     * <li>2    DCE security tsUUID
     * <li>3    Name-based tsUUID
     * <li>4    Randomly generated tsUUID
     * </ul>
     *
     * @return  the version number of this <tt>tsUUID</tt>.
     */
    public int version() {
        if (version < 0) {
            // Version is bits masked by 0x000000000000F000 in MS long
            version = (int)((mostSigBits >> 12) & 0x0f);
        }
        return version;
    }

    /**
     * The variant number associated with this <tt>tsUUID</tt>. The variant
     * number describes the layout of the <tt>tsUUID</tt>.
     *
     * The variant number has the following meaning:<p>
     * <ul>
     * <li>0    Reserved for NCS backward compatibility
     * <li>2    The Leach-Salz variant (used by this class)
     * <li>6    Reserved, Microsoft Corporation backward compatibility
     * <li>7    Reserved for future definition
     * </ul>
     *
     * @return  the variant number of this <tt>tsUUID</tt>.
     */
    public int variant() {
        if (variant < 0) {
            // This field is composed of a varying number of bits
            if ((leastSigBits >>> 63) == 0) {
                variant = 0;
            } else if ((leastSigBits >>> 62) == 2) {
                variant = 2;
            } else {
                variant = (int)(leastSigBits >>> 61);
            }
        }
        return variant;
    }

    /**
     * The timestamp value associated with this tsUUID.
     *
     * <p>The 60 bit timestamp value is constructed from the time_low,
     * time_mid, and time_hi fields of this <tt>tsUUID</tt>. The resulting
     * timestamp is measured in 100-nanosecond units since midnight,
     * October 15, 1582 UTC.<p>
     *
     * The timestamp value is only meaningful in a time-based tsUUID, which
     * has version type 1. If this <tt>tsUUID</tt> is not a time-based tsUUID then
     * this method throws UnsupportedOperationException.
     *
     * @throws UnsupportedOperationException if this tsUUID is not a
     *         version 1 tsUUID.
     */
    public long timestamp() {
        if (version() != 1) {
            throw new UnsupportedOperationException("Not a time-based UUID");
        }
        long result = timestamp;
        if (result < 0) {
            result = (mostSigBits & 0x0000000000000FFFL) << 48;
            result |= ((mostSigBits >> 16) & 0xFFFFL) << 32;
            result |= mostSigBits >>> 32;
            timestamp = result;
        }
        return result;
    }

    /**
     * The clock sequence value associated with this tsUUID.
     *
     * <p>The 14 bit clock sequence value is constructed from the clock
     * sequence field of this tsUUID. The clock sequence field is used to
     * guarantee temporal uniqueness in a time-based tsUUID.<p>
     *
     * The  clockSequence value is only meaningful in a time-based tsUUID, which
     * has version type 1. If this tsUUID is not a time-based tsUUID then
     * this method throws UnsupportedOperationException.
     *
     * @return  the clock sequence of this <tt>tsUUID</tt>.
     * @throws UnsupportedOperationException if this tsUUID is not a
     *         version 1 tsUUID.
     */
    public int clockSequence() {
        if (version() != 1) {
            throw new UnsupportedOperationException("Not a time-based UUID");
        }
        if (sequence < 0) {
            sequence = (int)((leastSigBits & 0x3FFF000000000000L) >>> 48);
        }
        return sequence;
    }

    /**
     * The node value associated with this tsUUID.
     *
     * <p>The 48 bit node value is constructed from the node field of
     * this tsUUID. This field is intended to hold the IEEE 802 address
     * of the machine that generated this tsUUID to guarantee spatial
     * uniqueness.<p>
     *
     * The node value is only meaningful in a time-based tsUUID, which
     * has version type 1. If this tsUUID is not a time-based tsUUID then
     * this method throws UnsupportedOperationException.
     *
     * @return  the node value of this <tt>tsUUID</tt>.
     * @throws UnsupportedOperationException if this tsUUID is not a
     *         version 1 tsUUID.
     */
    public long node() {
        if (version() != 1) {
            throw new UnsupportedOperationException("Not a time-based UUID");
        }
        if (node < 0) {
            node = leastSigBits & 0x0000FFFFFFFFFFFFL;
        }
        return node;
    }

    // Object Inherited Methods

    /**
     * Returns a <code>String</code> object representing this
     * <code>tsUUID</code>.
     *
     * <p>The tsUUID string representation is as described by this BNF :
     * <blockquote><pre>
     * {@code
     * tsUUID                   = <time_low> "-" <time_mid> "-"
     *                          <time_high_and_version> "-"
     *                          <variant_and_sequence> "-"
     *                          <node>
     * time_low               = 4*<hexOctet>
     * time_mid               = 2*<hexOctet>
     * time_high_and_version  = 2*<hexOctet>
     * variant_and_sequence   = 2*<hexOctet>
     * node                   = 6*<hexOctet>
     * hexOctet               = <hexDigit><hexDigit>
     * hexDigit               =
     *       "0" | "1" | "2" | "3" | "4" | "5" | "6" | "7" | "8" | "9"
     *       | "a" | "b" | "c" | "d" | "e" | "f"
     *       | "A" | "B" | "C" | "D" | "E" | "F"
     * }</pre></blockquote>
     *
     * @return  a string representation of this <tt>tsUUID</tt>.
     */
    public String toString() {
	return (digits(mostSigBits >> 32, 8) + "-" +
		digits(mostSigBits >> 16, 4) + "-" +
		digits(mostSigBits, 4) + "-" +
		digits(leastSigBits >> 48, 4) + "-" +
		digits(leastSigBits, 12));
    }

    /** Returns val represented by the specified number of hex digits. */
    private static String digits(long val, int digits) {
	long hi = 1L << (digits * 4);
	return Long.toHexString(hi | (val & (hi - 1))).substring(1);
    }

    /**
     * Returns a hash code for this <code>tsUUID</code>.
     *
     * @return  a hash code value for this <tt>tsUUID</tt>.
     */
    public int hashCode() {
        if (hashCode == -1) {
            hashCode = (int)((mostSigBits >> 32) ^
                             mostSigBits ^
                             (leastSigBits >> 32) ^
                             leastSigBits);
        }
        return hashCode;
    }

    /**
     * Compares this object to the specified object.  The result is
     * <tt>true</tt> if and only if the argument is not
     * <tt>null</tt>, is a <tt>tsUUID</tt> object, has the same variant,
     * and contains the same value, bit for bit, as this <tt>tsUUID</tt>.
     *
     * @param   obj   the object to compare with.
     * @return  <code>true</code> if the objects are the same;
     *          <code>false</code> otherwise.
     */
    public boolean equals(Object obj) {
	if (!(obj instanceof tsUUID))
	    return false;
        if (((tsUUID)obj).variant() != this.variant())
            return false;
        tsUUID id = (tsUUID)obj;
	return (mostSigBits == id.mostSigBits &&
                leastSigBits == id.leastSigBits);
    }

    // Comparison Operations

    /**
     * Compares this tsUUID with the specified tsUUID.
     *
     * <p>The first of two UUIDs follows the second if the most significant
     * field in which the UUIDs differ is greater for the first tsUUID.
     *
     * @param  val <tt>tsUUID</tt> to which this <tt>tsUUID</tt> is to be compared.
     * @return -1, 0 or 1 as this <tt>tsUUID</tt> is less than, equal
     *         to, or greater than <tt>val</tt>.
     */
    public int compareTo(tsUUID val) {
        // The ordering is intentionally set up so that the UUIDs
        // can simply be numerically compared as two numbers
        return (this.mostSigBits < val.mostSigBits ? -1 :
                (this.mostSigBits > val.mostSigBits ? 1 :
                 (this.leastSigBits < val.leastSigBits ? -1 :
                  (this.leastSigBits > val.leastSigBits ? 1 :
                   0))));
    }

    /**
     * Reconstitute the <tt>tsUUID</tt> instance from a stream (that is,
     * deserialize it). This is necessary to set the transient fields
     * to their correct uninitialized value so they will be recomputed
     * on demand.
     */
    private void readObject(java.io.ObjectInputStream in)
        throws java.io.IOException, ClassNotFoundException {

        in.defaultReadObject();

        // Set "cached computation" fields to their initial values
        version = -1;
        variant = -1;
        timestamp = -1;
        sequence = -1;
        node = -1;
        hashCode = -1;
    }

    public Date getDate() {

        return new Date (this.getLeastSignificantBits());

    }
}
