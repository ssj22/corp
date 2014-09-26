package net.corp.core.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.corp.core.model.LogBook;
import net.corp.core.model.LogMaterial;
import net.corp.core.model.PrimaryGroup;
import net.corp.core.model.StockItems;

@SuppressWarnings("serial")
public class StaticVO implements Serializable {
	private String tName;
	private String vName;
	private String iName;
	private String sName;
	
	public static void main(String[] args) {
		
		Map<String, Map<String, List<String>>> tmap = new HashMap<String, Map<String,List<String>>>();
		List<StaticVO> logMaterials = new ArrayList<StaticVO>();
		StaticVO s1 = new StaticVO(); s1.iName = "i1"; s1.sName = "S1"; s1.tName = "T1"; s1.vName = "V1";
		StaticVO s2 = new StaticVO(); s2.iName = "i2"; s2.sName = "S1"; s2.tName = "T1"; s2.vName = "V1";
		StaticVO s3 = new StaticVO(); s3.iName = "i3"; s3.sName = "S1"; s3.tName = "T1"; s3.vName = "V1";
		StaticVO s4 = new StaticVO(); s4.iName = "i1"; s4.sName = "S2"; s4.tName = "T2"; s4.vName = "V2";
		StaticVO s5 = new StaticVO(); s5.iName = "i5"; s5.sName = "S2"; s5.tName = "T2"; s5.vName = "V2";
		StaticVO s6 = new StaticVO(); s6.iName = "i2"; s6.sName = "S3"; s6.tName = "T3"; s6.vName = "V3";
		StaticVO s7 = new StaticVO(); s7.iName = "i7"; s7.sName = "S4"; s7.tName = "T4"; s7.vName = "V4";
		
		logMaterials.add(s1);logMaterials.add(s2);logMaterials.add(s3);logMaterials.add(s4);logMaterials.add(s5);logMaterials.add(s6);logMaterials.add(s7);
		
		
		
		if (logMaterials != null) {
			List<String> vList = new ArrayList<String>();
			List<String> iList = new ArrayList<String>();
			List<String> sList = null;
			for(StaticVO lm: logMaterials) {
				String tName = lm.tName;//lm.getLog().getTransport().getVendorName();
				String vName = lm.vName;//lm.getLog().getVehicle().getVehicleNumber();
				String iName = lm.iName;//lm.getItem().getStockItemname();
				String sName = lm.sName;//lm.getLog().getSiteName();
				Map<String, List<String>> subMap = null;
				if (tmap.containsKey(tName)) {
					subMap = tmap.get(tName);
					if (subMap.containsKey("s")) {
						sList = subMap.get("s");
					}
					else {
						sList = new ArrayList<String>();
						subMap.put("s", sList);
					}
					
					if (subMap.containsKey("i")) {
						iList = subMap.get("i");
					}
					else {
						iList = new ArrayList<String>();
						subMap.put("i", iList);
					}
					
					if (subMap.containsKey("v")) {
						vList = subMap.get("v");
					}
					else {
						vList = new ArrayList<String>();
						subMap.put("v", vList);
					}
				}
				else {
					subMap = new HashMap<String, List<String>>();
					vList = new ArrayList<String>();
					subMap.put("v", vList);
					iList = new ArrayList<String>();
					subMap.put("i", iList);
					sList = new ArrayList<String>();
					subMap.put("s", sList);
					tmap.put(tName, subMap);
				}
				
				
				if (!vList.contains(vName)) {
					vList.add(vName);
				}
				if (!sList.contains(sName)) {
					sList.add(sName);
				}
				if (!iList.contains(iName)) {
					iList.add(iName);
				}
			}
		}	
		
		System.out.println(tmap);
	}
		
}
