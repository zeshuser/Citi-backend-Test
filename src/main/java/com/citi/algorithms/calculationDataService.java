package com.citi.algorithms;

import com.citi.repositories.*;
import com.citi.entity.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;



@Repository
public class calculationDataService 
{
	@Autowired
	private CalculationsRepository traderepo;
	
	//private List<Trade> traders;
	
	public List<Trade> retrieveAllData(){
		List<Trade> traders = new ArrayList<>();
		//System.out.println("trying to retrieved");
		this.traderepo.findAll().forEach(traders::add);
		//System.out.println("trades retrieved");
		  Collections.sort(traders, new Comparator<Trade>() {
		        @Override
		        public int compare(Trade object1, Trade object2) {
		            return (int) (object1.getDate().compareTo(object2.getDate()));
		        }
		    });
		return traders;
	}
	
	

}
