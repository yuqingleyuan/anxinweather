package com.location_11dw.model.dqq;

import java.util.ArrayList;
import java.util.List;

import com.location_11dw.db.dqq.AnXinWeathherOpenHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class AnXinWeatherDB {

	/**
	 * 数据名
	 */
	public static final String DB_NAME="anxin_weather";
	/**
	 * 数据库版本
	 */
	public static final int WERSION=1;
	private static  AnXinWeatherDB anXinWeatherDB;
	
	private SQLiteDatabase db;
	/**
	 * @param context
	 * 将构造方法私有化
	 */
	private AnXinWeatherDB(Context context){
		AnXinWeathherOpenHelper dbHelper=new AnXinWeathherOpenHelper(context, DB_NAME, null, WERSION);
		db=dbHelper.getWritableDatabase();
	}
	/**
	 * @param context
	 * @return
	 * 获取AnXinWeatherDB的实力
	 */
	public synchronized static AnXinWeatherDB getInstance(Context context){
		if(anXinWeatherDB==null){
			anXinWeatherDB=new AnXinWeatherDB(context);
		}
		return anXinWeatherDB;
	}
	/**
	 * @param province
	 * 将Province 实力存储到数据库
	 */
	public void saveProvince(Province province){
		if(province!=null){
			ContentValues values=new ContentValues();
			values.put("province_name", province.getProvinceName());
			values.put("province_code", province.getProvinceCode());
			db.insert("Province", null, values);
		}
	}
	/**
	 * @return
	 * 从数据库读取全国所有的省份信息
	 */
	public List<Province> loadProvince(){
		List<Province> list=new ArrayList<Province>();
		Cursor cursor=db.query("Province", null, null, null, null, null, null);
		if(cursor.moveToFirst()){
			do{
				Province province=new Province();
				province.setId(cursor.getInt(cursor.getColumnIndex("id")));
				province.setProvinceName(cursor.getString(cursor.getColumnIndex("province_name")));
				province.setProvinceCode(cursor.getString(cursor.getColumnIndex("province_code")));
				list.add(province);
			}while(cursor.moveToNext());
		}
		return list;
	}
	/**
	 * @param city
	 * 将City实力存储到数据库
	 */
	public void saveCity(City city){
		if(city!=null){
			ContentValues values=new ContentValues();
			values.put("city_name", city.getCityName());
			values.put("city_code", city.getCityCode());
			values.put("province_id", city.getProvinceId());
			db.insert("City", null, values);
		}
	}
	/**
	 * @param provinceId
	 * @return
	 * 从数据库读取某省下所有的城市信息
	 */
	public List<City> loadCities(int provinceId){
		List<City> list=new ArrayList<City>();
		Cursor cursor=db.query("City", null, "province_id=?", new String[]{String.valueOf(provinceId)}, null, null, null);
		if(cursor.moveToFirst()){
			do{
				City city=new City();
				city.setId(cursor.getInt(cursor.getColumnIndex("id")));
				city.setCityName(cursor.getString(cursor.getColumnIndex("city_name")));
				city.setCityCode(cursor.getString(cursor.getColumnIndex("city_code")));
				city.setProvinceId(provinceId);
				list.add(city);
			}while(cursor.moveToNext());
		}
		return list;
		
	}
	/**
	 * @param county
	 * 将County实力存储到数据库
	 */
	public void saveCountry(County county){
		if(county!=null){
			ContentValues values=new ContentValues();
			values.put("county_name", county.getCountyName());
			values.put("county_code", county.getCountyCode());
			values.put("city_id", county.getCityId());
			db.insert("County", null, values);
		}
	}
	/**
	 * @param cityId
	 * @return
	 * 从数据库读取某城市下所有的县信息
	 */
	public List<County> loadCounties(int cityId){
		List<County> list=new ArrayList<County>();
		Cursor cursor=db.query("County", null, "city_id=?", new String[]{String.valueOf(cityId)}, null, null, null);
		if(cursor.moveToFirst()){
			do{
				County county=new County();
				county.setId(cursor.getInt(cursor.getColumnIndex("id")));
				county.setCountyName(cursor.getString(cursor.getColumnIndex("county_name")));
				county.setCountyCode(cursor.getString(cursor.getColumnIndex("county_code")));
				county.setCityId(cityId);
				list.add(county);
			}while(cursor.moveToNext());
			
		}
		return list;
		
	}
}
