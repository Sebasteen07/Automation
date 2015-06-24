package com.intuit.ihg.product.support.utils;

public class WebserviceUtils {

    /**
     * Print out the results of an object in a readable format
     * 
     * @param result
     */
    public static void dumpResults(Object result) {
        Object[] array = { result };
        dumpResults(array);
    }

    /**
     * Print out the results of object arrays in a readable format
     * 
     * @param results
     */
    public static void dumpResults(Object[] results) {
        if (results != null) {
            System.out.println("Got " + results.length + " results");

            for (int i = 0; i < results.length; i++) {
                Object obj = results[i];

                System.out.println("\nResult: " + obj.getClass().getName()
                        + " Data: " + obj);

            }
        } else {
            System.out.println("Got no results");
        }

        lineBreak();
    }

    private static void lineBreak() {
        System.out
                .println("\n------------------------------------------------------------\n");
    }

    // Returns the contents of the file in a byte array.
//    private static byte[] getBytesFromFile(File file) throws IOException {
//        InputStream is = new FileInputStream(file);
//
//        // Get the size of the file
//        long length = file.length();
//
//        // You cannot create an array using a long type.
//        // It needs to be an int type.
//        // Before converting to an int type, check
//        // to ensure that file is not larger than Integer.MAX_VALUE.
//        if (length > Integer.MAX_VALUE) {
//            // File is too large
//        }
//
//        // Create the byte array to hold the data
//        byte[] bytes = new byte[(int) length];
//
//        // Read in the bytes
//        int offset = 0;
//        int numRead = 0;
//        while (offset < bytes.length
//                && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
//            offset += numRead;
//        }
//
//        // Ensure all the bytes have been read in
//        if (offset < bytes.length) {
//    		  is.close();
//            throw new IOException("Could not completely read file "
//                    + file.getName());
//        }
//
//        // Close the input stream and return bytes
//        is.close();
//        return bytes;
//    }

}
