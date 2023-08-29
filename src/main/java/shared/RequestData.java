package shared;

import java.io.Serializable;

import org.apache.logging.log4j.LogManager;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class RequestData implements Serializable {
        protected String hobby;
        protected String aversion; 
        public RequestData() {}
        
        @NotNull
        @Pattern(regexp=".*\\S.*", message="cannot be empty")
        public String getHobby() {
                return hobby;
        }

        public void setHobby(String hobby) {
                LogManager.getLogger(RequestData.class).info("Setting Hobby: " + hobby); 
                this.hobby = hobby;
        }
        
        @Pattern(regexp=".*\\S.*", message="cannot be empty")
        @NotNull
        public String getAversion() {
                return aversion;
        }

        public void setAversion(String aversion) {
                LogManager.getLogger(RequestData.class).info("Setting aversion: " + aversion);
                this.aversion = aversion;
        }
        
     }
