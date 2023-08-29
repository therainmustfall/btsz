package shared;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public class PersistentBase {
        
        public PersistentBase() { }
        
        protected Long id;

        @Id
        @GeneratedValue
        public Long getId() { return id;}

        private void setId(Long id) { this.id = id;}
              
}
