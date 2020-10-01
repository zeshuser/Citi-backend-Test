package com.citi.Test;

import com.citi.controller.*;
import com.citi.entity.FixedIncomeSecurity;
import com.citi.repositories.*;
import com.citi.entity.*;
import com.citi.algorithms.*;


import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;


import java.util.*;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CalculationServiceTest {
	
	
	public static List<FixedIncomeSecurity> setmasterdb(){
		FixedIncomeSecurity f1 = new FixedIncomeSecurity("IN1015467857", 100, "GOVERNMENT OF INDIA T-BILL", 0, 2020, 9, 30);
		f1.setOpeningfund(1989081786);
		f1.setOpeningprice(98);
		f1.setOpeningqty(15497);
		f1.setFinalprice(99);
		f1.setBonusdate(null);
		FixedIncomeSecurity f2= new FixedIncomeSecurity("INE002A14F01", 1000, "RELIANCE INDUSTRIES COMMERCIAL PAPER", 6, 2020, 02, 15, 2021,
				02, 15, "a/360");
		f2.setOpeningfund(1989081786);
		f2.setOpeningprice(1003);
		f2.setOpeningqty(15784);
		f2.setFinalprice(1021);
		f2.setBonusdate(null);
		FixedIncomeSecurity f3 = new FixedIncomeSecurity("INE002A14F02", 1000, "STATE BANK OF INDIA CD", 4, 2020, 06, 30, 2021, 04, 30,"a/365");
		f3.setOpeningfund(1989081786);
		f3.setOpeningprice(1005);
		f3.setOpeningqty(15812);
		f3.setFinalprice(970);
		f3.setBonusdate(null);
		FixedIncomeSecurity f4 = new FixedIncomeSecurity("INE002A14F03", 1000, "ABG SHIPYARD LTD. DEBENTURE", 5, 2020, 04, 10, 2026, 8, 10,"a/366");
		f4.setOpeningfund(1989081786);
		f4.setOpeningprice(1019);
		f4.setOpeningqty(15767);
		f4.setFinalprice(1027);
		f4.setBonusdate(new GregorianCalendar(2020,10,17));
		FixedIncomeSecurity f5 = new FixedIncomeSecurity("INE002A14F04", 100, "GOVERNMENT OF INDIA BOND", 4.5f, 2020, 10, 25, 2021, 06, 27,	"30/360");
		f5.setOpeningfund(1989081786);
		f5.setOpeningqty(15083);
		f5.setOpeningprice(97);
		f5.setFinalprice(100);
		f5.setBonusdate(null);
		List<FixedIncomeSecurity> masterdb = Arrays.asList(f1,f2,f3,f4,f5);
		return masterdb;
	}
	
	List<FixedIncomeSecurity> masterdb = setmasterdb();

	
//	List<FixedIncomeSecurity> masterdb = Arrays.asList(
//			new FixedIncomeSecurity("IN1015467857", 100, "GOVERNMENT OF INDIA T-BILL", 0, 2020, 9, 30),
//			new FixedIncomeSecurity("INE002A14F01", 1000, "RELIANCE INDUSTRIES COMMERCIAL PAPER", 6, 2020, 02, 15, 2021,
//					02, 15, "a/360"),
//			new FixedIncomeSecurity("INE002A14F02", 1000, "STATE BANK OF INDIA CD", 4, 2020, 06, 30, 2021, 04, 30,
//					"a/365"),
//			new FixedIncomeSecurity("INE002A14F03", 1000, "ABG SHIPYARD LTD. DEBENTURE", 5, 2020, 04, 10, 2026, 8, 10,
//					"a/366"),
//			new FixedIncomeSecurity("INE002A14F04", 100, "GOVERNMENT OF INDIA BOND", 4.5f, 2020, 10, 25, 2021, 06, 27,
//					"30/360"));
	
	
	
	GregorianCalendar gc1 = new GregorianCalendar(2020,05,05);

	//Trade t = new Trade("GOVERNMENT OF INDIA BOND","INE002A14F04",98.0,455,gc1,"sell",100,(float)4.5,gc1,gc1,"30/360",gc1);
	List<Trade> tradelist = Arrays.asList(
			new Trade("GOVERNMENT OF INDIA BOND","INE002A14F04",99.0,471,new GregorianCalendar(2020,4,9),"Sell",100,(float)4.5,new GregorianCalendar(2020,11,25),new GregorianCalendar(2021,11,25),"30/360",null),
			new Trade("GOVERNMENT OF INDIA BOND","INE002A14F04",102.0,441,new GregorianCalendar(2020,8,13),"Buy",100,(float)4.5,new GregorianCalendar(2020,11,25),new GregorianCalendar(2021,7,27),"30/360",null)
			//new Trade("GOVERNMENT OF INDIA T-BILL","IN1015467857",100,15197,new GregorianCalendar(2020,10,30),"Sell",100,(float)0.0,null,new GregorianCalendar(2020,10,30),null,null)
			);
	
	
	
	@Mock
	calculationDataService calcdatamock;
	
	@InjectMocks
	CalculationsService calcservice;
	
	
	@Test
	public void TestCouponIncome() {
		when(calcdatamock.retrieveAllData()).thenReturn(tradelist);
		double[] result1 = new double[5];
		result1[0]=calcservice.Couponincome(masterdb).get("RELIANCE INDUSTRIES COMMERCIAL PAPER");
		result1[1]=calcservice.Couponincome(masterdb).get("GOVERNMENT OF INDIA T-BILL");
		result1[2]=calcservice.Couponincome(masterdb).get("ABG SHIPYARD LTD. DEBENTURE");
		result1[3]=calcservice.Couponincome(masterdb).get("GOVERNMENT OF INDIA BOND");
		result1[4]=calcservice.Couponincome(masterdb).get("STATE BANK OF INDIA CD");
		double[] o1 = {947040.0,0.0,788350.0,67738.5,632480.0};
		assertTrue(Arrays.equals(o1,result1));
	}
	
	@Test
	public void TestAccCouponIncome() {
		when(calcdatamock.retrieveAllData()).thenReturn(tradelist);
		double[] result2 = new double[5];
		result2[0]=calcservice.Accruedcouponincome(masterdb).get("RELIANCE INDUSTRIES COMMERCIAL PAPER");
		result2[1]=calcservice.Accruedcouponincome(masterdb).get("GOVERNMENT OF INDIA T-BILL");
		result2[2]=calcservice.Accruedcouponincome(masterdb).get("ABG SHIPYARD LTD. DEBENTURE");
		result2[3]=calcservice.Accruedcouponincome(masterdb).get("GOVERNMENT OF INDIA BOND");
		result2[4]=calcservice.Accruedcouponincome(masterdb).get("STATE BANK OF INDIA CD");
		double[] o2 = {762893.3333333334,0.0,504027.04918032786,6773.85,265121.7534246575};
		assertTrue(Arrays.equals(o2,result2));
	}
	@Test
	public void TestPnL() {
		when(calcdatamock.retrieveAllData()).thenReturn(tradelist);
		double[] result2 = new double[5];
		result2[0]=calcservice.PLpersecurity(masterdb,new GregorianCalendar(2020,12,30)).get("RELIANCE INDUSTRIES COMMERCIAL PAPER");
		result2[1]=calcservice.PLpersecurity(masterdb,new GregorianCalendar(2020,12,30)).get("GOVERNMENT OF INDIA T-BILL");
		result2[2]=calcservice.PLpersecurity(masterdb,new GregorianCalendar(2020,12,30)).get("ABG SHIPYARD LTD. DEBENTURE");
		result2[3]=calcservice.PLpersecurity(masterdb,new GregorianCalendar(2020,12,30)).get("GOVERNMENT OF INDIA BOND");
		result2[4]=calcservice.PLpersecurity(masterdb,new GregorianCalendar(2020,12,30)).get("STATE BANK OF INDIA CD");
		double[] o2 = {0.0,0.0,0.0,942.0,0.0};
		assertTrue(Arrays.equals(o2,result2));
	}
	
	@Test
	public void UTestPnL() {
		when(calcdatamock.retrieveAllData()).thenReturn(tradelist);
		double[] result2 = new double[5];
		result2[0]=calcservice.UPLpersecurity(masterdb).get("RELIANCE INDUSTRIES COMMERCIAL PAPER");
		result2[1]=calcservice.UPLpersecurity(masterdb).get("GOVERNMENT OF INDIA T-BILL");
		result2[2]=calcservice.UPLpersecurity(masterdb).get("ABG SHIPYARD LTD. DEBENTURE");
		result2[3]=calcservice.UPLpersecurity(masterdb).get("GOVERNMENT OF INDIA BOND");
		result2[4]=calcservice.UPLpersecurity(masterdb).get("STATE BANK OF INDIA CD");
		for (double element: result2) {
            System.out.println(element);
        }
		double[] o2 = {284112.0,15497.0,126136.0,43020.89996135014,-553420.0};
		assertTrue(Arrays.equals(o2,result2));
	}
	
	
	
}
