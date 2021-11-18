package tn.esprit.doctormobile.Model;

public class Doctor {


        private String name;
        private int rating;
        private String description;
        private int image;

        public Doctor() {
        }

        public Doctor(String name, int image) {
            this.name = name;
            this.rating = rating;
            this.image = image;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getRating() {
            return rating;
        }

        public void setRating(int rating) {
            this.rating = rating;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public int getImage() {
            return image;
        }

        public void setImage(int image) {
            this.image = image;
        }
    }


