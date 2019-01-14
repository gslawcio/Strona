package dao;

import java.util.List;

import model.Discovery;

public interface DiscoveryDAO extends GenericDAO<Discovery, Long>{

	List<Discovery> getAll();
	
}