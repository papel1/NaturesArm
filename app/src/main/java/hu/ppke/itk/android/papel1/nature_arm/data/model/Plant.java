package hu.ppke.itk.android.papel1.nature_arm.data.model;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.io.Serializable;
import java.util.Date;

import hu.ppke.itk.android.papel1.nature_arm.data.converters.TimeStampConverter;
import hu.ppke.itk.android.papel1.nature_arm.data.converters.UriConverter;

@Entity(tableName = "Plants")
public class Plant implements Serializable, Parcelable
{
    /*Serializable*/
    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo(name = "name")
    public String name;
    @ColumnInfo(name = "description")
    public String description;
    @ColumnInfo(name = "location")
    public String location;
    @ColumnInfo(name = "imageUri")
    @TypeConverters({UriConverter.class})
    public Uri imageUri;
    @ColumnInfo(name = "wateringFreq")
    public int wateringFreq;
    @ColumnInfo(name = "fertilizingFreq")
    public int fertilizingFreq;
    @ColumnInfo(name = "transplantingFreq")
    public int transplantingFreq;
    @ColumnInfo(name = "lat")
    public Double lat;
    @ColumnInfo(name = "lon")
    public Double lon;
    @ColumnInfo(name = "lastWateringDate")
    @TypeConverters({TimeStampConverter.class})
    public Date lastWateringDate;
    @ColumnInfo(name = "lastFertilizingDate")
    @TypeConverters({TimeStampConverter.class})
    public Date lastFertilizingDate;
    @ColumnInfo(name = "lastTransplantingDate")
    @TypeConverters({TimeStampConverter.class})
    public Date lastTransplantingDate;

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getLocation()
    {
        return location;
    }

    public void setLocation(String location)
    {
        this.location = location;
    }

    public Uri getImageUri()
    {
        return imageUri;
    }

    public void setImageUri(Uri imageUri)
    {
        this.imageUri = imageUri;
    }

    public int getWateringFreq()
    {
        return wateringFreq;
    }

    public void setWateringFreq(int wateringFreq)
    {
        this.wateringFreq = wateringFreq;
    }

    public int getFertilizingFreq()
    {
        return fertilizingFreq;
    }

    public void setFertilizingFreq(int fertilizingFreq)
    {
        this.fertilizingFreq = fertilizingFreq;
    }

    public int getTransplantingFreq()
    {
        return transplantingFreq;
    }

    public void setTransplantingFreq(int transplantingFreq)
    {
        this.transplantingFreq = transplantingFreq;
    }

    public Double getLat()
    {
        return lat;
    }

    public void setLat(Double lat)
    {
        this.lat = lat;
    }

    public Double getLon()
    {
        return lon;
    }

    public void setLon(Double lon)
    {
        this.lon = lon;
    }

    public Date getLastWateringDate()
    {
        return lastWateringDate;
    }

    public void setLastWateringDate(Date lastWateringDate)
    {
        this.lastWateringDate = lastWateringDate;
    }

    public Date getLastFertilizingDate()
    {
        return lastFertilizingDate;
    }

    public void setLastFertilizingDate(Date lastFertilizingDate)
    {
        this.lastFertilizingDate = lastFertilizingDate;
    }

    public Date getLastTransplantingDate()
    {
        return lastTransplantingDate;
    }

    public void setLastTransplantingDate(Date lastTransplantingDate)
    {
        this.lastTransplantingDate = lastTransplantingDate;
    }

    public Plant()
    {
    }

    /*Parcelable*/

    public Plant(Parcel in)
    {
        id = in.readInt();
        name = in.readString();
        description = in.readString();
        location = in.readString();
        imageUri = in.readParcelable(Uri.class.getClassLoader());
        wateringFreq = in.readInt();
        fertilizingFreq = in.readInt();
        transplantingFreq = in.readInt();
        if (in.readByte() == 0)
        {
            lat = null;
        } else
        {
            lat = in.readDouble();
        }
        if (in.readByte() == 0)
        {
            lon = null;
        } else
        {
            lon = in.readDouble();
        }
    }

    public static final Creator<Plant> CREATOR = new Creator<Plant>()
    {
        @Override
        public Plant createFromParcel(Parcel in)
        {
            return new Plant(in);
        }

        @Override
        public Plant[] newArray(int size)
        {
            return new Plant[size];
        }
    };

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(description);
        dest.writeString(location);
        dest.writeParcelable(imageUri, flags);
        dest.writeInt(wateringFreq);
        dest.writeInt(fertilizingFreq);
        dest.writeInt(transplantingFreq);
        if (lat == null)
        {
            dest.writeByte((byte) 0);
        } else
        {
            dest.writeByte((byte) 1);
            dest.writeDouble(lat);
        }
        if (lon == null)
        {
            dest.writeByte((byte) 0);
        } else
        {
            dest.writeByte((byte) 1);
            dest.writeDouble(lon);
        }
    }
}
