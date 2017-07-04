package com.qainfotech.tap.training.resourceio;

import com.qainfotech.tap.training.resourceio.exceptions.ObjectNotFoundException;
import com.qainfotech.tap.training.resourceio.model.Individual;
import com.qainfotech.tap.training.resourceio.model.Team;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.internal.Yaml;

/**
 *
 * @author Ramandeep RamandeepSingh AT QAInfoTech.com
 */
public class TeamsYamlReader {

	/**
	 * get a list of individual objects from db yaml file
	 * 
	 * @return
	 * @throws FileNotFoundException
	 */
	public List<Individual> getListOfIndividuals() throws FileNotFoundException {

		File file = new File("src/main/resources/db.yaml");
		InputStream fis = null;

		fis = new FileInputStream(file);
		org.yaml.snakeyaml.Yaml yaml = new org.yaml.snakeyaml.Yaml();

		Map<String, ArrayList> map = (Map<String, ArrayList>) yaml.load(fis);

		ArrayList<Individual> individualList = map.get("individuals");

		List<Individual> myList = new ArrayList<>();

		for (int a = 0; a < individualList.size(); a++) {

			Map<String, ArrayList> inside = (Map<String, ArrayList>) individualList.get(a);
			Map temp = new HashMap();
			temp.put("id", inside.get("id"));
			temp.put("name", inside.get("name"));
			temp.put("active", inside.get("active"));

			myList.add(new Individual(temp));

		}

		return myList;

	}

	/**
	 * get individual object by id
	 * 
	 * @param id
	 *            individual id
	 * @return
	 * @throws com.qainfotech.tap.training.resourceio.exceptions.ObjectNotFoundException
	 * @throws FileNotFoundException
	 */
	public Individual getIndividualById(Integer id) throws FileNotFoundException, ObjectNotFoundException {
		TeamsYamlReader yamlreader = new TeamsYamlReader();
		List<Individual> list = yamlreader.getListOfIndividuals();

		
		int flag = 0, a;
		
		for (a = 0; a < list.size(); a++) {

			
			int getId = list.get(a).getId();
			
			
			if (getId == id) {
				flag = 1;		
				break;
			}

		}
		if (flag == 1) {
			return list.get(a);

		} else
			//System.out.println("inidvidual id "+individualId);
			throw new ObjectNotFoundException("Individual", "id", id.toString());
		
	}

	/**
	 * get individual object by name
	 * 
	 * @param name
	 * @return
	 * @throws com.qainfotech.tap.training.resourceio.exceptions.ObjectNotFoundException
	 * @throws FileNotFoundException
	 */
	public Individual getIndividualByName(String name) throws ObjectNotFoundException, FileNotFoundException {
		TeamsYamlReader yamlreader = new TeamsYamlReader();
		List<Individual> list = yamlreader.getListOfIndividuals();
		String individualName = name;
		int flag = 0, a;
		List<Individual> singleIndividual = new ArrayList<Individual>();
		for (a = 0; a < list.size(); a++) {

			if (list.get(a).getName().equals(individualName)) {
				flag = 1;
				break;

			}

		}
		if (flag == 1) {
			return list.get(a);

		} else
			throw new ObjectNotFoundException("Individual", "Name", name);
	}

	/**
	 * get a list of individual objects who are not active
	 * 
	 * @return List of inactive individuals object
	 * @throws FileNotFoundException
	 */
	public List<Individual> getListOfInactiveIndividuals() throws FileNotFoundException {
		TeamsYamlReader yamlreader = new TeamsYamlReader();
		List<Individual> list = yamlreader.getListOfIndividuals();

		List<Individual> inactiveindividuals = new ArrayList<Individual>();
		for (int a = 0; a < list.size(); a++) {

			if (list.get(a).isActive().equals(false)) {
				inactiveindividuals.add(list.get(a));
			}

		}
		return inactiveindividuals;
	}

	/**
	 * get a list of individual objects who are active
	 * 
	 * @return List of active individuals object
	 * @throws FileNotFoundException
	 */
	public List<Individual> getListOfActiveIndividuals() throws FileNotFoundException {
		TeamsYamlReader yamlreader = new TeamsYamlReader();
		List<Individual> list = yamlreader.getListOfIndividuals();

		List<Individual> activeindividuals = new ArrayList<Individual>();
		for (int a = 0; a < list.size(); a++) {

			if (list.get(a).isActive().equals(true)) {
				activeindividuals.add(list.get(a));
			}

		}
		return activeindividuals;
	}

	/**
	 * get a list of team objects from db yaml
	 * 
	 * @return
	 * @throws FileNotFoundException
	 * @throws ObjectNotFoundException
	 */
	public List<Team> getListOfTeams() throws FileNotFoundException, ObjectNotFoundException {
		File file = new File("src/main/resources/db.yaml");
		InputStream fis = null;

		fis = new FileInputStream(file);
		org.yaml.snakeyaml.Yaml yaml = new org.yaml.snakeyaml.Yaml();

		Map<String, ArrayList> map = (Map<String, ArrayList>) yaml.load(fis);

		ArrayList<Team> teamlist = map.get("teams");
		List<Team> list = new ArrayList<>();

		TeamsYamlReader reader = new TeamsYamlReader();

		for (int i = 0; i < teamlist.size(); i++) {
			List<Individual> individualList = new ArrayList<>();
			Map<String, ArrayList> singleteam = (Map<String, ArrayList>) teamlist.get(i);
			Map temp = new HashMap();
			temp.put("id", singleteam.get("id"));
			temp.put("name", singleteam.get("name"));
			// List<Individual> memberlist=new ArrayList<>();

			List memberlist = singleteam.get("members"); // ERROR yaha h

			for (int j = 0; j < memberlist.size(); j++) {

				int id = (Integer) memberlist.get(j);
				individualList.add(reader.getIndividualById(id));
			}

			temp.put("members", individualList);

			list.add(new Team(temp));
		}

		return list;
	}
	

}
