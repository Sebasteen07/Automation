package com.medfusion.rcm.pojo;

public class StatementGuarantorInformation{
    
    // required
    private String guarantorFirstName;
    private String guarantorLastName;

    // optional
    private String guarantorID;
    private String guarantorRelationshiptoPatient;    
    private StatementGuarantorAddress guarantorAddress;    

    public String getGuarantorFirstName() {
        return guarantorFirstName;
    }

    public String getGuarantorLastName() {
        return guarantorLastName;
    }

    public String retrieveAddress1() {
        return guarantorAddress.address1;
    }

    public String retrieveCity() {
        return guarantorAddress.city;
    }


    public String retrieveState() {
        return guarantorAddress.state;
    }

    public String retrieveZipCode() {
        return guarantorAddress.zipCode;
    }

    public String getGuarantorID() {
        return guarantorID;
    }

    public String getGuarantorRelationshiptoPatient() {
        return guarantorRelationshiptoPatient;
    }

    public String retrieveAddress2() {
        return guarantorAddress.address2;
    }

    public String retrieveCountry() {
        return guarantorAddress.country;
    }
    
    public StatementGuarantorAddress getGuarantorAddress() {
        return guarantorAddress;
    }
    
    /**
     * 
     * @param guarantorFirstName required
     * @param guarantorLastName required
     * @param address1 required
     * @param city required
     * @param state required
     * @param zipCode required
     * @param guarantorId optional, IF PRESENT, MUST BE PARSABLE TO INT
     * @param guarantorRelationshiptoPatient optional
     * @param address2 optional
     * @param country optional
     */
    public StatementGuarantorInformation(String guarantorFirstName, String guarantorLastName, String address1, String city,
            String state, String zipCode, String guarantorID, String guarantorRelationshiptoPatient, String address2,
            String country){
        if (isEmptyOrNull(guarantorFirstName)
                || isEmptyOrNull(guarantorLastName)
                || isEmptyOrNull(address1)
                || isEmptyOrNull(city)
                || isEmptyOrNull(state)
                || isEmptyOrNull(zipCode)){
            throw new IllegalArgumentException("Required parameters were not present, see javadoc!");
        }
        else{
            this.guarantorFirstName = guarantorFirstName;
            this.guarantorLastName = guarantorLastName;
            this.guarantorID = guarantorID;
            this.guarantorRelationshiptoPatient = guarantorRelationshiptoPatient;
            
            this.guarantorAddress = new StatementGuarantorAddress(address1, address2, city, country, state, zipCode);
        }
    }
    // copy constructor
    public StatementGuarantorInformation(StatementGuarantorInformation other){
        this(other.getGuarantorFirstName(), other.getGuarantorLastName(), other.retrieveAddress1(),
                other.retrieveCity(), other.retrieveState(), other.retrieveZipCode(), 
                other.getGuarantorID(), other.getGuarantorRelationshiptoPatient(), other.retrieveAddress2(),
                other.retrieveCountry());
    }
    
    public class StatementGuarantorAddress{
        private String address1;        
        private String address2;        
        private String city;
        private String state;        
        private String zipCode;
        private String country;
                
        public StatementGuarantorAddress(String address1, String address2, String city, String country,
                String state, String zipCode) {            
                this.address1 = address1;
                this.address2 = address2;
                this.city = city;
                this.country = country;
                this.state = state;
                this.zipCode = zipCode;
            }
        public String getAddress1() {
            return address1;
        }

        public String getAddress2() {
            return address2;
        }

        public String getCity() {
            return city;
        }

        public String getState() {
            return state;
        }

        public String getZipCode() {
            return zipCode;
        }

        public String getCountry() {
            return country;
        }


    }
    private boolean isEmptyOrNull(String str){
        return (str == null || str.isEmpty());
    }           
}