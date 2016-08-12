    package name_differences.models;

    import java.util.Date;

    public class AuthorDto {
        private int id;
        private String name;
        private Date bornOn;

        /**
         * This constructor is needed by the framework.
         * If you don't have any constructors, a parameterless
         * one will be created by default. Once you define a constructor
         * you need to define a parameterless one as well
         */
        public AuthorDto() {

        }

        public AuthorDto(int id, String name, Date bornOn) {
            this.setId(id);
            this.setName(name);
            this.setBornOn(bornOn);
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Date getBornOn() {
            return bornOn;
        }

        public void setBornOn(Date bornOn) {
            this.bornOn = bornOn;
        }
    }
