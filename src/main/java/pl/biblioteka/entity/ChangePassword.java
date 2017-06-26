package pl.biblioteka.entity;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Size;

/**
 * Created by papi on 17.06.17.
 */
public class ChangePassword {

    @NotEmpty(message = "To pole nie może być puste")
    @Size(min = 4, message = "To pole musi posiadać minimum 4 znaki")
    private String oldPassword;

    @NotEmpty(message = "To pole nie może być puste")
    @Size(min = 4, message = "To pole musi posiadać minimum 4 znaki")
    private String newPassword;

    @NotEmpty(message = "To pole nie może być puste")
    @Size(min = 4, message = "To pole musi posiadać minimum 4 znaki")
    private String confirmNewPassword;

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmNewPassword() {
        return confirmNewPassword;
    }

    public void setConfirmNewPassword(String confirmNewPassword) {
        this.confirmNewPassword = confirmNewPassword;
    }

    @Override
    public String toString() {
        return "ChangePassword{" +
                "oldPassword='" + oldPassword + '\'' +
                ", newPassword='" + newPassword + '\'' +
                ", confirmNewPassword='" + confirmNewPassword + '\'' +
                '}';
    }
}
