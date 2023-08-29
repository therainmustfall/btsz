package shared;


import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;
import org.apache.logging.log4j.core.config.builder.api.AppenderComponentBuilder;
import org.apache.logging.log4j.core.config.builder.api.ConfigurationBuilder;
import org.apache.logging.log4j.core.config.builder.api.ConfigurationBuilderFactory;
import org.apache.logging.log4j.core.config.builder.api.LayoutComponentBuilder;
import org.apache.logging.log4j.core.config.builder.api.RootLoggerComponentBuilder;
import org.apache.logging.log4j.core.config.builder.impl.BuiltConfiguration;

import jakarta.servlet.http.HttpServlet;
public class InitLog4j extends HttpServlet {
        public void init() {
                String initPath = getInitParameter("logPath");
                String logPath = "/WEB-INF/logs/error.log";
                if (initPath != null) logPath = getServletContext().getRealPath(initPath);
                initLogger(logPath,Level.INFO);
                Logger classLogger = LogManager.getLogger(InitLog4j.class);
                //LogManager.getRootLogger().error("show me where am I, I am root logger");
                classLogger.info("logging initialized.");
                //classLogger.error("wo is die log");
        }
        private void initLogger(String name, Level level)
        {
            ConfigurationBuilder<BuiltConfiguration> builder = 
                    ConfigurationBuilderFactory.newConfigurationBuilder();
            AppenderComponentBuilder console = builder.newAppender("stdout","Console");
            builder.add(console);

            AppenderComponentBuilder file = builder.newAppender("log","File");
            file.addAttribute("fileName", name);
            builder.add(file);
/**
*          AppenderComponentBuilder rollingFile = 
*                   builder.newAppender("rolling","RollingFile");
*            rollingFile.addAttribute("fileName","rolling.log");
*            rollingFile.addAttribute("filePattern","rolling-%d{MM-dd-yy}.log.gz");
*           builder.add(rollingFile);
*
*            FilterComponentBuilder flow = builder.newFilter("MarkerFilter",Filter.Result.ACCEPT,Filter.Result.DENY);  
*            flow.addAttribute("marker", "FLOW");
*            console.add(flow);
*/
            LayoutComponentBuilder standard = builder.newLayout("PatternLayout");
            standard.addAttribute("pattern", "%d [%t] %-5level: %msg%n%throwable");

            console.add(standard);
            file.add(standard);
            // rolling.add(standard);
            RootLoggerComponentBuilder rootLogger = builder.newRootLogger(level);
            rootLogger.add(builder.newAppenderRef("stdout"));
            rootLogger.add(builder.newAppenderRef("log"));
            builder.add(rootLogger);

            Configurator.initialize(builder.build());
        }
}
