package com.ferrumlabs;

import io.swagger.annotations.Contact;
import io.swagger.annotations.Info;
import io.swagger.annotations.License;
import io.swagger.annotations.SwaggerDefinition;

@SwaggerDefinition(
        info = @Info(
                description = "Retrieves verses from various religious texts",
                version = "V0.1",
                title = "Sacred Text API",
                termsOfService = "http://sacredtextapi.ferrumlabs.com/terms.html",
                contact = @Contact(
                   name = "Joel Holmes", 
                   email = "Holmes89@gmail.com", 
                   url = "http://sacredtextapi.ferrumlabs.com"
                ),
                license = @License(
                   name = "Apache 2.0", 
                   url = "http://www.apache.org/licenses/LICENSE-2.0"
                )
        ),
        consumes = {"application/json"},
        produces = {"application/json"},
        schemes = {SwaggerDefinition.Scheme.HTTP}
)
public interface ISacredTextAPIConfig {

}
