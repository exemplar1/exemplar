package org.exemplar.foundation.service.configuration;

import com.google.common.base.Strings;
import org.jasypt.util.text.StrongTextEncryptor;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import java.util.Properties;

public class EncryptedPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {

    private static final String DEFAULT_ENCRYPT_PASSWORD = "t0Ps3cr3t";

    private StrongTextEncryptor encryptor;

    public EncryptedPropertyPlaceholderConfigurer() {
        encryptor = new StrongTextEncryptor();
        String encryptPassword = System.getProperty("enc.password");
        encryptor.setPassword(
                Strings.isNullOrEmpty(encryptPassword) ? DEFAULT_ENCRYPT_PASSWORD : encryptPassword
        );
    }

    @Override
    protected String resolvePlaceholder(String placeholder, Properties props) {
        String property = super.resolvePlaceholder(placeholder, props);
        if(property.startsWith("ENC(")) {
            return encryptor.decrypt(property.substring(4, property.length() - 1));
        }
        return property;
    }
}
