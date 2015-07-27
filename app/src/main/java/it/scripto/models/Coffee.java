package it.scripto.models;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import it.scripto.bargino.BuildConfig;
import it.scripto.bargino.R;
import it.scripto.util.Util;

/**
 * Class that represent an italian coffee.
 * @author pincopallino93
 * @version 1.0
 */
public class Coffee implements Parcelable {

    /**
     * Debug TAG.
     */
    private final String TAG = getClass().getCanonicalName();

    /**
     * Static context.
     */
    private static Context context = null;

    /**
     * Cost of base coffee.
     */
    public static final float COFFEE_COST = 0.70f;

    /**
     * Cost of milk in a coffee.
     */
    public static final float MILK_COST = 0.10f;

    /**
     * Cost of liquor in a coffee.
     */
    public static final float LIQUOR_COST = 0.20f;

    /**
     * Cost in Euro of coffee.
     */
    private Float cost;

    /**
     * Type of coffee.
     */
    public static final Integer GLASS = 0;
    public static final Integer CUP = 1;
    private Integer type;

    /**
     * Type of sugar of coffee.
     */
    public static final Integer CANE = 0;
    public static final Integer BITTER = 1;
    public static final Integer SWEET = 2;
    public static final Integer SWEETENER = 3;
    private Integer sugar;

    /**
     * Boolean that show if is a milked coffee or not.
     */
    private Boolean isWhitMilk;

    /**
     * Type of milk in a milked coffee (if it is not milked it is set to -1).
     */
    public static final Integer HOT_MILK = 0;
    public static final Integer COLD_MILK = 1;
    private Integer milk;

    /**
     * Boolean that show if is a corretto coffee or not.
     */
    private Boolean isWithLiquor;

    /**
     * Type of liquor in a corretto coffee (if it is not corretto it is set to null).
     */
    public static final String GRAPPA = "Grappa";
    public static final String SAMBUCA = "Sambuca";
    public static final String COGNAC = "Cognac";
    private String liquor;

    /**
     * Quantity of coffee.
     */
    public static final Integer SHORT = 0;
    public static final Integer NORMAL = 1;
    public static final Integer LONG = 2;
    public static final Integer DOUBLE = 3;
    private Integer quantity;

    /**
     * Default constructor.
     */
    public Coffee() {

        context = Util.getContext();

        this.cost = COFFEE_COST;
        this.type = CUP;
        this.sugar = SWEET;
        this.isWhitMilk = false;
        this.milk = -1;
        this.isWithLiquor = false;
        this.liquor = null;
        this.quantity = NORMAL;

        if (BuildConfig.DEBUG) {
            Log.v(TAG, "costructor");
        }
    }

    /**
     * Method that allow to set the type of the coffee.
     * @param type The type of the coffee, accepted CUP or GLASS.
     * @throws Exception if the type of coffee is not valid.
     */
    public void setType(Integer type) throws Exception {
        if (type.equals(CUP) || type.equals(GLASS)) {
            this.type = type;
        } else {
            throw new Exception("You can only set CUP or GLASS coffee's type!");
        }

        if (BuildConfig.DEBUG) {
            Log.v(TAG, "setType");
        }
    }

    /**
     * Method that allow to set the sugar of the coffee.
     * @param sugar The sugar of the coffee, accepted CANE or BITTER or SWEET or SWEETENER.
     * @throws Exception if the type of coffee is not valid.
     */
    public void setSugar(Integer sugar) throws Exception {
        if (sugar.equals(CANE) || sugar.equals(BITTER) || sugar.equals(SWEET) || sugar.equals(SWEETENER)) {
            this.sugar = sugar;
        } else {
            throw new Exception("You can set only CANE, BITTER, SWEET or SWEETENER!");
        }

        if (BuildConfig.DEBUG) {
            Log.v(TAG, "setSugar");
        }
    }

    /**
     * Method that allow to add milk to the coffee. If not set the type of milk it is set to
     * cold milk.
     * @throws Exception if milk was already present in the coffee.
     */
    public void addMilk() throws Exception {
        if (!this.isWhitMilk && !this.isWithLiquor) {
            this.isWhitMilk = true;
            this.milk = COLD_MILK; // Set default milk
            this.cost += MILK_COST; // Increase cost of the coffee
        } else {
            throw new Exception("You can add milk only if the coffee does not have already milk or liquor!");
        }

        if (BuildConfig.DEBUG) {
            Log.v(TAG, "addMilk");
        }
    }

    /**
     * Method that allow to add a certain type of the milk to the coffee.
     * @param typeMilk The type of milk, accepted HOT_MILK or COLD_MILK.
     * @throws Exception if the type of the milk is not valid.
     */
    public void addMilk(int typeMilk) throws Exception {
        this.addMilk(); // Call default

        if (typeMilk == HOT_MILK || typeMilk == COLD_MILK) {
            this.milk = typeMilk;
        } else {
            throw new Exception("You can only add HOT_MILK or COLD_MILK milk!");
        }

        if (BuildConfig.DEBUG) {
            Log.v(TAG, "addMilk");
        }
    }

    /**
     * Method that allow to remove milk from the coffee.
     * @throws Exception if the type of the milk is not valid.
     */
    public void removeMilk() throws Exception {
        if (this.isWhitMilk && (this.milk == HOT_MILK || this.milk == COLD_MILK)) {
            this.isWhitMilk = false;
            this.milk = -1;
            this.cost -= MILK_COST; // Decrease cost of the coffee
        } else {
            throw new Exception("You can only remove milk if is present in the coffee!");
        }

        if (BuildConfig.DEBUG) {
            Log.v(TAG, "removeMilk");
        }
    }

    /**
     * Method that allow to add liquor to the coffee. If not set the type of liquor it is set to
     * GRAPPA.
     * @throws Exception if liquor was already present in the coffee.
     */
    public void addLiquor() throws Exception {
        if (!this.isWithLiquor && !this.isWhitMilk) {
            this.isWithLiquor = true;
            this.liquor = GRAPPA; // Set default liquor
            this.cost += LIQUOR_COST; // Increase cost of the coffee
        } else {
            throw new Exception("You can add a liquor only if the coffee does not have already liquor or milk!");
        }

        if (BuildConfig.DEBUG) {
            Log.v(TAG, "addLiquor");
        }
    }

    /**
     * Method that allow to add a certain type of the liquor to the coffee.
     * @param typeLiquor The type of liquor, accepted GRAPPA, SAMBUCA or COGNAC.
     * @throws Exception if the type of the milk is not valid.
     */
    public void addLiquor(String typeLiquor) throws Exception {
        this.addLiquor(); // Call default

        if (typeLiquor.equals(GRAPPA) || typeLiquor.equals(SAMBUCA) || typeLiquor.equals(COGNAC)) {
            this.liquor = typeLiquor;
        } else {
            throw new Exception("You can only add GRAPPA, SAMBUCA or COGNAC liquor!");
        }

        if (BuildConfig.DEBUG) {
            Log.v(TAG, "addLiquor");
        }
    }

    /**
     * Method that allow to remove liquor from the coffee.
     * @throws Exception if the type of the milk is not valid.
     */
    public void removeLiquor() throws Exception {
        if (this.isWithLiquor && (this.liquor.equals(GRAPPA) || this.liquor.equals(SAMBUCA) || this.liquor.equals(COGNAC))) {
            this.isWithLiquor = false;
            this.liquor = null;
            this.cost -= LIQUOR_COST; // Decrease cost of the coffee
        } else {
            throw new Exception("You can only remove liquor if is present in the coffee!");
        }

        if (BuildConfig.DEBUG) {
            Log.v(TAG, "removeLiquor");
        }
    }

    /**
     * Method that allow to set the quantity of the coffee.
     * @param quantity The sugar of the coffee, accepted NORMAL or SHORT or LONG or DOUBLE.
     * @throws Exception if the quantity of coffee is not valid.
     */
    public void setQuantity(Integer quantity) throws Exception {
        if (quantity.equals(NORMAL) || quantity.equals(SHORT) || quantity.equals(LONG) || quantity.equals(DOUBLE)) {
            this.quantity = quantity;
        } else {
            throw new Exception("You can only set a NORMAL, SHORT, LONG or DOUBLE quantity!");
        }

        if (BuildConfig.DEBUG) {
            Log.v(TAG, "setQuantity");
        }
    }

    /**
     * Method that allow to see if in the coffee there is milk or not.
     * @return true if there is milk, false otherwise.
     */
    public Boolean hasMilk() {
        if (BuildConfig.DEBUG) {
            Log.v(TAG, "hasMilk");
        }
        return this.isWhitMilk;
    }

    /**
     * Method that allow to see if in the coffee there is liquor or not.
     * @return true if there is liquor, false otherwise.
     */
    public Boolean hasLiquor() {
        if (BuildConfig.DEBUG) {
            Log.v(TAG, "hasLiquor");
        }
        return this.isWithLiquor;
    }

    @Override
    public String toString() {
        return String.format("\nCost: %.2f €\nType: %d\nSugar: %d\nTypeMilk: %d\nTypeLiquor: %s\nQuantity: %d\n", this.cost, this.type, this.sugar, this.milk, this.liquor, this.quantity);
    }

    /**
     * Method that allow to return a coffee instance in a legible string
     * @return the coffee in a user legible string
     */
    public String summarize() {
        String summary;
        if (isWithLiquor) {
            summary = String.format(context.getString(R.string.summary_long), this.getStringQuantity(), this.getStringSugar(), this.getStringType(), this.liquor.toLowerCase());
        } else if (isWhitMilk) {
            summary = String.format(context.getString(R.string.summary_long), this.getStringQuantity(), this.getStringSugar(), this.getStringType(), this.getStringMilk());
        } else {
            summary = String.format(context.getString(R.string.summary_short), this.getStringQuantity(), this.getStringSugar(), this.getStringType());
        }
        return summary;
    }

    /**
     * This method allow to return the quantity of the coffee in "human's way".
     * @return the string in lowercase that represent the quantity of the coffee
     */
    private String getStringQuantity() {
        String quantity;
        switch (this.quantity) {
            case 0:
                quantity = context.getString(R.string.shortq);
                break;
            case 1:
                quantity = context.getString(R.string.normal);
                break;
            case 2:
                quantity = context.getString(R.string.longq);
                break;
            case 3:
                quantity = context.getString(R.string.doubleq);
                break;
            default:
                quantity = context.getString(R.string.unknown);
        }

        return quantity.toLowerCase();
    }

    /**
     * This method allow to return type of the coffee in "human's way".
     * @return the string in lowercase that represent the type of the coffee
     */
    private String getStringType() {
        String type;
        switch (this.type) {
            case 0:
                type = context.getString(R.string.glass);
                break;
            case 1:
                type = context.getString(R.string.cup);
                break;
            default:
                type = context.getString(R.string.unknown);
        }

        return type.toLowerCase();
    }

    /**
     * This method allow to return sugar in the coffee in "human's way".
     * @return the string in lowercase that represent the sugar in the coffee
     */
    private String getStringSugar() {
        String sugar;
        switch (this.sugar) {
            case 0:
                sugar = context.getString(R.string.cane);
                break;
            case 1:
                sugar = context.getString(R.string.bitter);
                break;
            case 2:
                sugar = context.getString(R.string.sweet);
                break;
            case 3:
                sugar = context.getString(R.string.sweetener);
                break;
            default:
                sugar = context.getString(R.string.unknown);
        }

        return sugar.toLowerCase();
    }

    /**
     * This method allow to return milk in the coffee in "human's way".
     * @return the string in lowercase that represent the type of the milk in the coffee
     */
    public String getStringMilk() {
        String milk;
        switch (this.milk) {
            case 0:
                milk = context.getString(R.string.hot_milk);
                break;
            case 1:
                milk = context.getString(R.string.cold_milk);
                break;
            default:
                milk = context.getString(R.string.unknown);
        }

        return milk.toLowerCase();
    }

    /**
     * This method allow to return cost of coffee in "human's way".
     * @return the string in lowercase that represent the cost of the coffee
     */
    public String getStringTotal() {
        String total;
        if (isWhitMilk) {
            total = String.format("%.2f€ + %.2f€ = %.2f€", COFFEE_COST, MILK_COST, this.cost);
        } else if (isWithLiquor) {
            total = String.format("%.2f€ + %.2f€ = %.2f€", COFFEE_COST, LIQUOR_COST, this.cost);
        } else {
            total = String.format("%.2f€", this.cost);
        }

        return total;
    }

    /**
     * Method that allow to get type of the coffee.
     * @return the type of the coffee.
     */
    public Integer getType() {
        return type;
    }

    /**
     * Method that allow to get the sugar of the coffee.
     * @return the sugar of the coffee.
     */
    public Integer getSugar() {
        return sugar;
    }

    /**
     * Method that allow to get the milk of the coffee.
     * @return the milk of the coffee.
     */
    public Integer getMilk() {
        return milk;
    }

    /**
     * Method that allow to get the liquor of the coffee.
     * @return the liquor of the coffee.
     */
    public String getLiquor() {
        return liquor;
    }

    /**
     * Method that allow to get the quantity of the coffee.
     * @return the quantity of the coffee.
     */
    public Integer getQuantity() {
        return quantity;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.cost);
        dest.writeValue(this.type);
        dest.writeValue(this.sugar);
        dest.writeValue(this.isWhitMilk);
        dest.writeValue(this.milk);
        dest.writeValue(this.isWithLiquor);
        dest.writeString(this.liquor);
        dest.writeValue(this.quantity);
    }

    private Coffee(Parcel in) {
        this.cost = (Float) in.readValue(Float.class.getClassLoader());
        this.type = (Integer) in.readValue(Integer.class.getClassLoader());
        this.sugar = (Integer) in.readValue(Integer.class.getClassLoader());
        this.isWhitMilk = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.milk = (Integer) in.readValue(Integer.class.getClassLoader());
        this.isWithLiquor = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.liquor = in.readString();
        this.quantity = (Integer) in.readValue(Integer.class.getClassLoader());
    }

    public static final Parcelable.Creator<Coffee> CREATOR = new Parcelable.Creator<Coffee>() {
        public Coffee createFromParcel(Parcel source) {
            return new Coffee(source);
        }

        public Coffee[] newArray(int size) {
            return new Coffee[size];
        }
    };
}
