package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Discovery;
import model.User;
import model.Vote;
import model.VoteType;
import service.DiscoveryService;
import service.VoteService;

@WebServlet("/vote")
public class VoteController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User loggerUser = (User)request.getSession().getAttribute("user");
		if(loggerUser != null) {
			VoteType voteType = VoteType.valueOf(request.getParameter("vote"));
			long userId = loggerUser.getId();
			long discoveryId = Long.parseLong(request.getParameter("discovery_id"));
			updateVote(userId,discoveryId,voteType);
		}
		response.sendRedirect(request.getContextPath()+"/");
	}


	private void updateVote(long userId, long discoveryId, VoteType voteType) {

		VoteService voteService = new VoteService();
		Vote existingVote = voteService.getVoteByDiscoveryUserId(discoveryId, userId);
		Vote updateVote = voteService.addOrUpdateVote(discoveryId, userId, voteType);
		if(existingVote != updateVote || !updateVote.equals(existingVote)) {
			updateDiscovery(discoveryId,existingVote,updateVote);
		}
	}


	private void updateDiscovery(long discoveryId, Vote oldVote, Vote newVote) {

		DiscoveryService discoveryService = new DiscoveryService();
		Discovery discoveryById = discoveryService.getDiscoveryById(discoveryId);
		Discovery updateDiscovery = null;
		if(oldVote == null && newVote != null) {
			updateDiscovery = addDiscovery(discoveryById,newVote.getVoteType());
		}else if(oldVote != null && newVote != null) {
			updateDiscovery = removeDiscoveryVote(discoveryById,oldVote.getVoteType());
			updateDiscovery = addDiscovery(updateDiscovery,newVote.getVoteType());
		}
		discoveryService.updateDiscovery(updateDiscovery);
	}


	


	private Discovery addDiscovery(Discovery discovery, VoteType voteType) {
		Discovery copyDiscovery = new Discovery(discovery);
		if(voteType == VoteType.VOTE_UP) {
			copyDiscovery.setUpVote(copyDiscovery.getUpVote()+1);
		} else if(voteType == VoteType.VOTE_DOWN) {
			copyDiscovery.setDownVote(copyDiscovery.getDownVote()+1);
		}
		return copyDiscovery;
	}
	
	private Discovery removeDiscoveryVote(Discovery discovery, VoteType voteType) {
		Discovery copyDiscovery = new Discovery(discovery);
		if(voteType == VoteType.VOTE_UP) {
			copyDiscovery.setUpVote(copyDiscovery.getUpVote()-1);
		}else if(voteType == VoteType.VOTE_DOWN) {
			copyDiscovery.setDownVote(copyDiscovery.getDownVote()-1);
		}
		return copyDiscovery;
		}
}
