package shared;

import java.io.Serializable;

import org.apache.logging.log4j.LogManager;

import jakarta.persistence.Entity;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@Entity
public class RequestDataPersistent extends PersistentBase implements Serializable {
        protected String hobby;
        protected String aversion;
        protected int daysPerWeek;

        @Min(value=1,message="must be greater than 1")
        @Max(value=7,message="cannot be greater than 7")
        public int getDaysPerWeek() {
                return daysPerWeek;
        }

        public void setDaysPerWeek(int daysPerWeek) {
                this.daysPerWeek = daysPerWeek;
        }

        public RequestDataPersistent() { super(); }
        
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
        
        @Transient
        public boolean isValidHobby() { return hobby != null && !hobby.trim().equals("");}
     }
