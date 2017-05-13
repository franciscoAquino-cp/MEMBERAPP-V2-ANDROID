package services.model;

import android.database.Cursor;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import database.table.Table;

/**
 * <p>
 *     This Procedure serve also as database model
 * </p>
 */
public class Procedure implements Table.Procedure {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("serviceClassCode")
    @Expose
    private String serviceClassCode;
    @SerializedName("procedureCode")
    @Expose
    private String procedureCode;
    @SerializedName("procedureDesc")
    @Expose
    private String procedureDesc;
    @SerializedName("procedureAmount")
    @Expose
    private Integer procedureAmount;

    public Procedure(Cursor cursor) {
        setId(cursor.getInt(cursor.getColumnIndex(ID)));
        setServiceClassCode(cursor.getString(cursor.getColumnIndex(SERVICE_CLASS_CODE)));
        setProcedureCode(cursor.getString(cursor.getColumnIndex(CODE)));
        setProcedureDesc(cursor.getString(cursor.getColumnIndex(DESCRIPTION)));
        setProcedureAmount(cursor.getInt(cursor.getColumnIndex(AMOUNT)));
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getServiceClassCode() {
        return serviceClassCode;
    }

    public void setServiceClassCode(String serviceClassCode) {
        this.serviceClassCode = serviceClassCode;
    }

    public String getProcedureCode() {
        return procedureCode;
    }

    public void setProcedureCode(String procedureCode) {
        this.procedureCode = procedureCode;
    }

    public String getProcedureDesc() {
        return procedureDesc;
    }

    public void setProcedureDesc(String procedureDesc) {
        this.procedureDesc = procedureDesc;
    }

    public Integer getProcedureAmount() {
        return procedureAmount;
    }

    public void setProcedureAmount(Integer procedureAmount) {
        this.procedureAmount = procedureAmount;
    }

    public static final String getTableStructure() {
        return "CREATE TABLE " +
                TABLE_NAME + " ( " +
                ID + " INTEGER, " +
                SERVICE_CLASS_CODE + " TEXT ," +
                CODE + " TEXT ," +
                DESCRIPTION + " TEXT ," +
                AMOUNT + " INTEGER  )";
    }

}