/*package utils;


import static org.assertj.core.api.Assertions.assertThat;

import com.gym.utils.Utils;
import org.junit.jupiter.api.Test;

public class PasswordGeneratorTest {
    @Test
    public void testGeneratePassword() {
        //Given
        String upperCase = "ABCDEFGHIJKLMNOPQRSTUVWYXZ";
        String lowerCase = "abcdefghijklmnopqrstuvwxyz";
        String digits = "0123456789";
        String symbols = "!@#$%^&*()-=+?<>";
        String sumStr = upperCase + lowerCase + digits + symbols;
        //When
        String password = Utils.generatePassword();
        //Assert
        assertThat(password).isNotNull();
        assertThat(password.length()).isEqualTo(10);
        assertThat(sumStr.toCharArray()).containsAnyOf(password.toCharArray());
    }
}*/
