package com.example.tours.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

    @Entity
    @Table(name="users")
    public class Users {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        @NotBlank(message="Molimo unesite Vašu email adresu.")
        @Email(message = "Unesite ispravnu email adresu.")
        @Column(unique = true, nullable = false, length = 50)
        private String email;

        @NotBlank(message="Molimo unesite Vašu lozinku.")
        @Column(nullable = false)
        private String password;
        @NotBlank(message="Molimo ponovite Vašu lozinku.")
        private String passwordRepeat;

        private boolean passwordsEqual;

        public void setPasswordsEqual(boolean passwordsEqual) {
            this.passwordsEqual = passwordsEqual;
        }

        public String getPasswordRepeat() {
            return passwordRepeat;
        }

        public void setPasswordRepeat(String passwordRepeat) {
            this.passwordRepeat = passwordRepeat;
        }


        @Size(min=2, max=30, message="Vaše ime mora biti između 2 i 30 znakova duljine.")
        @Column(nullable = false, length = 30)
        private String firstname;


        @Size(min=2, max=30, message="Vaše prezime mora biti između 2 i 30 znakova duljine.")
        @Column(nullable = false, length = 30)
        private String lastname;

        @Column()
        private String gender;

        @Column(nullable = false)
        private int roleId;
        //if roleId == 1 role = user
        //if roleId == 2 role = admin

        @Column()
        private String dateOfBirth;


        @Column(nullable = false)
        private boolean  isDeleted;


        @Column(nullable = false)
        private String dateOfCreation;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getFirstname() {
            return firstname;
        }

        public void setFirstname(String firstname) {
            this.firstname = firstname;
        }

        public String getLastname() {
            return lastname;
        }

        public void setLastname(String lastname) {
            this.lastname = lastname;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public int getRoleId() {
            return roleId;
        }

        public void setRoleId(int roleId) {
            this.roleId = roleId;
        }

        public String getDateOfBirth() {
            return dateOfBirth;
        }

        public void setDateOfBirth(String dateOfBirth) {
            this.dateOfBirth = dateOfBirth;
        }

        public boolean isDeleted() {
            return isDeleted;
        }

        public void setDeleted(boolean deleted) {
            isDeleted = deleted;
        }

        public String getDateOfCreation() {
            return dateOfCreation;
        }

        public void setDateOfCreation(String dateOfCreation) {
            this.dateOfCreation = dateOfCreation;
        }

        public String getFullname(){return this.getFirstname() + " " + this.getLastname();}
        public Users(Long id, String email, String password, String passwordRepeat, boolean passwordsEqual, String firstname, String lastname, String gender, int roleId, String dateOfBirth, boolean isDeleted, String dateOfCreation) {
            this.id = id;
            this.email = email;
            this.password = password;
            this.passwordRepeat = passwordRepeat;
            this.passwordsEqual = passwordsEqual;
            this.firstname = firstname;
            this.lastname = lastname;
            this.gender = gender;
            this.roleId = roleId;
            this.dateOfBirth = dateOfBirth;
            this.isDeleted = isDeleted;
            this.dateOfCreation = dateOfCreation;
        }
        public Users(){}

        @AssertTrue(message="Lozinke se moraju podudarati.")
        public boolean isPasswordsEqual () {
            try {
                return this.password.equals(this.passwordRepeat);
            } catch (NullPointerException e) {
                return false;
            }
        }
    }


