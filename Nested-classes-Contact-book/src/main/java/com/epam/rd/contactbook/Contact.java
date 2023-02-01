package com.epam.rd.contactbook;

import java.util.Arrays;

public class Contact {
    private ContactInfo[] contactInfo;
    private String contactName;

    public Contact(String contactName) {
        if(contactName != null && !contactName.equals("")) {
            this.contactInfo = new ContactInfo[0];
            this.contactName = contactName;
        }
    }

    public static class Email implements ContactInfo {
        String title;
        String value;

        public Email(String title, String value){
            this.value = value;
            this.title = title;
        }

        @Override
        public String getTitle() {
            return title;
        }

        @Override
        public String getValue() {
            return value;
        }
    }
    public static class Social implements ContactInfo {
        String title;
        String value;

        public Social(String title, String value){
            this.value = value;
            this.title = title;
        }
        @Override
        public String getTitle() {
            return title;
        }
        @Override
        public String getValue() {
            return value;
        }
    }
    private class NameContactInfo implements ContactInfo{
        //no fields!
        @Override
        public String getTitle() {
            return "Name";
        }

        @Override
        public String getValue() {
            return contactName;
        }
    }

    public void rename(String newName) {
        if (newName != null && !newName.equals("")) {
            contactName = newName;
        }
    }

    public Email addEmail(String localPart, String domain) {
        int count = 0;
        for (ContactInfo info : contactInfo) {
            if(info instanceof Email)
                count++;
        }
        if(count >= 3){
            return null;
        } else {
            contactInfo = Arrays.copyOf(contactInfo, contactInfo.length+1);
            contactInfo[contactInfo.length-1] = new Email("Email", localPart + "@" + domain);
            return (Email) contactInfo[contactInfo.length-1];
        }

    }

    public Email addEpamEmail(String firstname, String lastname) {
        int count = 0;
        for (ContactInfo info : contactInfo) {
            if(info instanceof Email)
                count++;
        }
        if(count >= 3) {
            return null;
        } else {
            contactInfo = Arrays.copyOf(contactInfo, contactInfo.length+1);
            contactInfo[contactInfo.length-1] = new Email("Epam Email", firstname + "_" + lastname + "@epam.com") {
                @Override
                public String getTitle() {
                    return title;
                }
                @Override
                public String getValue() {
                    return value;
                }
            };
            return (Email) contactInfo[contactInfo.length-1];
        }
    }

    public ContactInfo addPhoneNumber(int code, String number) {
        for (ContactInfo info : contactInfo) {
            if(info.getTitle().equals("Tel"))
                return null;
        }
        contactInfo = Arrays.copyOf(contactInfo, contactInfo.length+1);
        contactInfo[contactInfo.length-1] = new ContactInfo() {
            @Override
            public String getTitle() {
                return "Tel";
            }
            @Override
            public String getValue() {
                return "+" + code + " " + number;
            }
        };
        return contactInfo[contactInfo.length-1];
    }

    public Social addTwitter(String twitterId) {
        int count = 0;
        for (ContactInfo info : contactInfo) {
            if(info instanceof Social)
                count++;
        }
        if(count >= 5) {
            return null;
        } else {
            contactInfo = Arrays.copyOf(contactInfo, contactInfo.length+1);
            contactInfo[contactInfo.length-1] = new Social("Twitter", twitterId);
            return (Social) contactInfo[contactInfo.length-1];
        }
    }

    public Social addInstagram(String instagramId) {
        int count = 0;
        for (ContactInfo info : contactInfo) {
            if(info instanceof Social)
                count++;
        }
        if(count >= 5) {
            return null;
        } else {
            contactInfo = Arrays.copyOf(contactInfo, contactInfo.length+1);
            contactInfo[contactInfo.length-1] = new Social("Instagram", instagramId);
            return (Social) contactInfo[contactInfo.length-1];
        }
    }

    public Social addSocialMedia(String title, String id) {
        int count = 0;
        for (ContactInfo info : contactInfo) {
            if(info instanceof Social)
                count++;
        }
        if(count >= 5) {
            return null;
        } else {
            contactInfo = Arrays.copyOf(contactInfo, contactInfo.length+1);
            contactInfo[contactInfo.length-1] = new Social(title, id);
            return (Social) contactInfo[contactInfo.length-1];
        }
    }

    public ContactInfo[] getInfo() {
        ContactInfo[] result = new ContactInfo[contactInfo.length+1];
        result[0] = new NameContactInfo();
        int i = 1;

        for (ContactInfo info : contactInfo) {
            if(info.getTitle().equals("Tel"))
                result[i++] = info;
        }
        for (ContactInfo info : contactInfo) {
            if(info instanceof Email)
                result[i++] = info;
        }
        for (ContactInfo info : contactInfo) {
            if(info instanceof Social)
                result[i++] = info;
        }
        return result;
    }

}
