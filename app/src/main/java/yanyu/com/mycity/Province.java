package yanyu.com.mycity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/12/14 0014.
 */

public class Province implements Parcelable {
    public String name;

    public List<City> city ;

    @Override
    public String toString() {
        return "Province{" +
                "name='" + name + '\'' +
                ", city=" + city +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeList(this.city);
    }

    public Province() {
    }

    protected Province(Parcel in) {
        this.name = in.readString();
        this.city = new ArrayList<City>();
        in.readList(this.city, City.class.getClassLoader());
    }

    public static final Parcelable.Creator<Province> CREATOR = new Parcelable.Creator<Province>() {
        @Override
        public Province createFromParcel(Parcel source) {
            return new Province(source);
        }

        @Override
        public Province[] newArray(int size) {
            return new Province[size];
        }
    };
}
