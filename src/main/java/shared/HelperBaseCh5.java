package  shared;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.hibernate.validator.internal.engine.path.PathImpl;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

public abstract class HelperBaseCh5 extends HelperBaseCh4 {

        protected static final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        protected static final Validator validator = validatorFactory.getValidator();
        private Map<String, String> errorMap = new HashMap<String,String>();
        public HelperBaseCh5(HttpServlet servlet, HttpServletRequest request, HttpServletResponse response)
        {
                super(servlet, request, response);
        }

        public void setErrors(Object data)
        {
                Set<ConstraintViolation<Object>> violations = validator.validate(data);
                errorMap.clear();

                if (!violations.isEmpty()) {
                        for (ConstraintViolation<Object> msg : violations) {
                                PathImpl value = (PathImpl) msg.getPropertyPath();
                                errorMap.put((String) value.getLeafNode().getName(), msg.getMessage());
                        }
                }
                LogManager.getLogger("shared ch5").info("Errors set, it is empty " + errorMap.toString());
        }
        public void clearErrors() { if (errorMap != null) errorMap.clear(); }
        public Map<String, String> getErrors() { return errorMap; }
        public boolean isValid(Object data) {
                setErrors(data);
                return errorMap.isEmpty();
        }
        public boolean isValidProperty(String name) {
                String msg = errorMap.get(name);
                return msg == null || msg.equals("");
        }
}
