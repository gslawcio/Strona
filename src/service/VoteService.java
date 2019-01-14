package service;

import java.sql.Timestamp;
import java.util.Date;

import dao.DAOFactory;
import dao.VoteDAO;
import model.Vote;
import model.VoteType;

public class VoteService {

	/*
	 * Klasa VoteService posiada dwie podstawowe metody s³u¿¹ce do dodania lub aktualizacji rekordu odpowiadaj¹cego danemu g³osowi(oddanemu przez u¿ytkownika na konkretn¹ treœæ).
	 *  Oprócz tego stworzyliœmy metodê addOrUpdateVote(), która przyda siê z tego powodu, ¿e mo¿emy wywo³aæ j¹ z klasy kontrolera, gdzie nie musimy dziêki temu sprawdzaæ,
	 *   czy dany g³os istnieje ju¿ w bazie, czy te¿ nie - zajmie siê tym w³aœnie nasze metoda w klasie serwisu. Metoda getVoteByDiscoveryUserId() pozwala na wyszukanie g³osu 
	 *   w bazie danych na podstawie id g³osuj¹cego oraz id znaleziska.
	 */
	
	public Vote addVote(long discoveryId,long userId,VoteType voteType) {
		Vote vote = new Vote();
		vote.setDiscoveryId(discoveryId);
		vote.setId(userId);
		vote.setDate(new Timestamp(new Date().getTime()));
		vote.setVoteType(voteType);
		DAOFactory daoFactory = DAOFactory.getDAOFactory();
		VoteDAO voteDAO = daoFactory.getVoteDAO();
		vote = voteDAO.create(vote);
		return vote;
	}
	
	public Vote updateVote(long discoveryId,long userId,VoteType voteType) {
		DAOFactory daoFactory = DAOFactory.getDAOFactory();
		VoteDAO voteDao = daoFactory.getVoteDAO();
		Vote voteToUpdate = voteDao.getVoteByUserIdDiscoveryId(userId,discoveryId);
		if(voteToUpdate != null) {
			voteToUpdate.setVoteType(voteType);
			voteDao.update(voteToUpdate);
		}
		return voteToUpdate;
	}
	
	public Vote addOrUpdateVote(long discoveryId,long userId,VoteType voteType) {
		DAOFactory factoryDao = DAOFactory.getDAOFactory();
		VoteDAO voteDao = factoryDao.getVoteDAO();
		Vote vote = voteDao.getVoteByUserIdDiscoveryId(userId, discoveryId);
		Vote resultVote = null;
		if(vote == null) {
			resultVote = addVote(discoveryId, userId, voteType);
		}else {
			resultVote = updateVote(discoveryId, userId, voteType);
		}
		return resultVote;
	}
	
	public Vote getVoteByDiscoveryUserId(long discoveryId,long userId) {
		DAOFactory factoryDao = DAOFactory.getDAOFactory();
		VoteDAO voteDao = factoryDao.getVoteDAO();
		Vote vote = voteDao.getVoteByUserIdDiscoveryId(userId, discoveryId);
		return vote;
	}
	
	
}
