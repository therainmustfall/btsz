package ch3.controMitBean;

import org.apache.logging.log4j.LogManager;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class RequestData {
        protected String hobby;
        protected String aversion;
        
        public RequestData() {}
        
        @NotNull
        @Pattern(regexp="c.*\\S.*", message="cannot be empty")
        public String getHobby() {
                if (isValidHobby()) return hobby;
                return "no hobby";
        }

        public void setHobby(String hobby) {
                LogManager.getLogger(RequestData.class).info("Setting Hobby: " + hobby); 
                this.hobby = hobby;
        }
        
        @Pattern(regexp="d.*\\S.*", message="cannot be empty")
        @NotNull
        public String getAversion() {
                if (isValidAversion()) return aversion;
                return "no aversion";
        }

        public void setAversion(String aversion) {
                LogManager.getLogger(RequestData.class).info("Setting aversion: " + aversion);
                this.aversion = aversion;
        }
        
        public boolean isValidHobby() {
                return hobby != null && !hobby.trim().equals("");
        }
        public boolean isValidAversion() {
                return aversion != null && !aversion.trim().equals("");
        }

}
