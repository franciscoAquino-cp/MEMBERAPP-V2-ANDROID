package model;

/**
 * Created by aljohnalbuera on 11/25/16.
 */

public class ChangePassword {

    private String username;

    private String newPassword;

    private String oldPassword;

    public String getUsername ()
    {
        return username;
    }

    public void setUsername (String username)
    {
        this.username = username;
    }

    public String getNewPassword ()
    {
        return newPassword;
    }

    public void setNewPassword (String newPassword)
    {
        this.newPassword = newPassword;
    }

    public String getOldPassword ()
    {
        return oldPassword;
    }

    public void setOldPassword (String oldPassword)
    {
        this.oldPassword = oldPassword;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [username = "+username+", newPassword = "+newPassword+", oldPassword = "+oldPassword+"]";
    }

}
