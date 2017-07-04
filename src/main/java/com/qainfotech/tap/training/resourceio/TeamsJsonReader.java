package com.qainfotech.tap.training.resourceio;

import com.qainfotech.tap.training.resourceio.exceptions.ObjectNotFoundException;
import com.qainfotech.tap.training.resourceio.model.Individual;
import com.qainfotech.tap.training.resourceio.model.Team;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author Ramandeep RamandeepSingh AT QAInfoTech.com
 */
public class TeamsJsonReader {

	/**
	 * get a list of individual objects from db json file
	 * 
	 * @return
	 */
	public List<Individual> getListOfIndividuals() {

		List<Individual> individualList = new ArrayList<>();
		try {
			
			JSONObject jsonFile = (JSONObject) (new JSONParser()).parse(new FileReader("src/main/resources/db.json"));		
			
			
			JSONArray individualArray = new JSONArray();
			individualArray = (JSONArray) jsonFile.get("individuals");
			Map<String, Object> individualMap = new HashMap<>();

			for (int i = 0; i < individualArray.size(); i++) {

				JSONObject singleObject = (JSONObject) individualArray.get(i);
				individualMap.put("name", (Object) singleObject.get("name"));
				individualMap.put("id", (Object) ((Long) singleObject.get("id")).intValue());
				individualMap.put("active", (Object) singleObject.get("active"));

				individualList.add(new Individual(individualMap));

			}
			
			
			//System.err.println(individualList);
			

		} catch (Exception e) {
			System.out.println(e);
		}
		return individualList;
		
	}

	/**
	 * get individual object by id
	 * 
	 * @param id
	 *            individual id
	 * @return
	 * @throws com.qainfotech.tap.training.resourceio.exceptions.ObjectNotFoundException
	 */
	public Individual getIndividualById(Integer id) throws ObjectNotFoundException {

		int i, j = 0;
		TeamsJsonReader reader = new TeamsJsonReader();
		List<Individual> individualList = new ArrayList<>();
		individualList = reader.getListOfIndividuals();
		Individual individual = null;
		for (i = 0; i < individualList.size(); i++) {
		  
			if (individualList.get(i).getId().equals(id)) {
				individual = individualList.get(i);
				j = 1;
				break;
			}
		}

		if (j == 0)
			throw new ObjectNotFoundException("individual", "id", id.toString());
		else
			return individual;
	}

	/**
	 * get individual object by name
	 * 
	 * @param name
	 * @return
	 * @throws com.qainfotech.tap.training.resourceio.exceptions.ObjectNotFoundException
	 */
	public Individual getIndividualByName(String name) throws ObjectNotFoundException {
	
		int i, j = 0;
		TeamsJsonReader reader = new TeamsJsonReader();
		List<Individual> individualList = new ArrayList<>();
		individualList = reader.getListOfIndividuals();
		Individual individual = null;
		for (i = 0; i < individualList.size(); i++) {
			if (individualList.get(i).getName().equals(name)) {
				individual = individualList.get(i);
				j = 1;
				break;
			}
		}

		if (j == 0)
			throw new ObjectNotFoundException("individual", "id", name.toString());
		else
			return individual;
	}

	/**
	 * get a list of individual objects who are not active
	 * 
	 * @return List of inactive individuals object
	 */
	public List<Individual> getListOfInactiveIndividuals() {

	    int i;
		TeamsJsonReader reader = new TeamsJsonReader();
		List<Individual> individualList = new ArrayList<>();

		List<Individual> inactiveIndividualList = new ArrayList<>();
		individualList = reader.getListOfIndividuals();
		for (i = 0; i < individualList.size(); i++) {
		//	System.out.println(individualList.get(i).getId().getClass().getName() + " " + name);
			if (individualList.get(i).isActive().compareTo(false)==0) {
				inactiveIndividualList.add(individualList.get(i));
			}
		}

			return inactiveIndividualList;
	
	
	}

	/**
	 * get a list of individual objects who are active
	 * 
	 * @return List of active individuals object
	 */
	public List<Individual> getListOfActiveIndividuals() {
		
	    int i;
		TeamsJsonReader reader = new TeamsJsonReader();
		List<Individual> individualList = new ArrayList<>();

		List<Individual> activeIndividualList = new ArrayList<>();
		individualList = reader.getListOfIndividuals();
		for (i = 0; i < individualList.size(); i++) {
		//	System.out.println(individualList.get(i).getId().getClass().getName() + " " + name);
			if (individualList.get(i).isActive().compareTo(true)==0) {
				activeIndividualList.add(individualList.get(i));
			}
		}

			return activeIndividualList;
	
	}

	/**
	 * get a list of team objects from db json
	 * 
	 * @return
	 */
	public List<Team> getListOfTeams() {

	Map<String ,Object> teamMap      =new HashMap<>();
	try
	{
	JSONObject jsonFile = (JSONObject) (new JSONParser()).parse(new FileReader("src/main/resources/db.json"));
	JSONArray teamArray = new JSONArray();
	teamArray = (JSONArray) jsonFile.get("teams");
	List<Team>       teamList       = new ArrayList<>();
	TeamsJsonReader reader = new TeamsJsonReader();
	
	for(int i=0;i<teamArray.size();i++)
	{

		List<Individual> individualList = new ArrayList<>();
		
		JSONObject singleObject = (JSONObject) teamArray.get(i);
		teamMap.put("name", (Object) singleObject.get("name"));
		teamMap.put("id", (Object) ((Long) singleObject.get("id")).intValue());
		
		JSONArray memberArray = (JSONArray)singleObject.get("members");
		
		for(int j=0;j<memberArray.size();j++)
		{
			individualList.add(reader.getIndividualById(((Long)memberArray.get(j)).intValue()));
	            		
		}

		teamMap.put("members", individualList);
		teamList.add(new Team(teamMap));
		
	}
//	System.out.println(teamList.size());
	return teamList;
	}
	catch(Exception e)
	{
		System.out.println(e);
	}
		
	return null;
	
	}
}







