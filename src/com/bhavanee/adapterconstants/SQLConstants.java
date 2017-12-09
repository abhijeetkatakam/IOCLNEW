package com.bhavanee.adapterconstants;

public class SQLConstants
{
	public static String Fetch_Details="SELECT p.PlantCode,p.MaterialNo, p.TankNo,p.DT,p.TM,p.ProductDip_P,p.ProductDip_s,p.Density,p.Temperature,p.Pressure,p.TankStatus,p.DipType,p.Remarks,g.GaugeType FROM Productmaster p INNER JOIN Gaugetype g on p.tankno=g.tankno"; 
	public static String Fetch_GaugeType="SELECT TOP 1 * FROM Gaugetype ORDER BY ID desc";
}
