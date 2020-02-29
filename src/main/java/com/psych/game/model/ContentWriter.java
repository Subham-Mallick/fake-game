package com.psych.game.model;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "contentWriters")
public class ContentWriter extends Employee {

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JsonIdentityReference
    @Getter
    @Setter
    Set<Question> editQuestions = new HashSet<>();

    public ContentWriter(){
    }

    public static final class ContentWriterBuilder {
        private String address;
        private String phoneNumber;
        private String email;
        private String name;
        private String saltedHashedPassword;

        public ContentWriterBuilder() {
        }

        public static ContentWriterBuilder aContentWriter() {
            return new ContentWriterBuilder();
        }

        public ContentWriterBuilder address(String address) {
            this.address = address;
            return this;
        }

        public ContentWriterBuilder phoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public ContentWriterBuilder email(String email) {
            this.email = email;
            return this;
        }

        public ContentWriterBuilder name(String name) {
            this.name = name;
            return this;
        }

        public ContentWriterBuilder saltedHashedPassword(String saltedHashedPassword) {
            this.saltedHashedPassword = saltedHashedPassword;
            return this;
        }

        public ContentWriter build() {
            ContentWriter contentWriter = new ContentWriter();
            contentWriter.setAddress(address);
            contentWriter.setPhoneNumber(phoneNumber);
            contentWriter.setEmail(email);
            contentWriter.setName(name);
            contentWriter.setSaltedHashedPassword(saltedHashedPassword);
            return contentWriter;
        }
    }
}
