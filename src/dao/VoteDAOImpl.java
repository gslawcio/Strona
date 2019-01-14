package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;


import model.Vote;
import model.VoteType;
import util.ConnectionProvider;

public class VoteDAOImpl implements VoteDAO{

	
	private final static String CREATE_VOTE = "INSERT INTO vote(discovery_id, user_id, date, type) "
			+ "VALUES (:discovery_id, :user_id, :date, :type);";
	private final static String READ_VOTE =  "SELECT vote_id, discovery_id, user_id, date, type "
			+ "FROM vote WHERE vote_id = :vote_id;";
	private final static String READ_VOTE_BY_DISCOVERY_USE_IDS = "SELECT vote_id, discovery_id, user_id, date, type "
			+ "FROM vote WHERE user_id = :user_id AND discovery_id = :discovery_id;";
	private final static String UPDATE_VOTE = "UPDATE vote SET date=:date, type=:type WHERE vote_id=:vote_id;";
	
	private NamedParameterJdbcTemplate template;
	
	public VoteDAOImpl() {
		template = new NamedParameterJdbcTemplate(ConnectionProvider.getDataSource());
	}
	
	
	@Override
	public Vote create(Vote vote) {
		Vote copyVote = new Vote(vote);
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("discovery_id", copyVote.getDiscoveryId());
		paramMap.put("user_id", copyVote.getUserId());
		paramMap.put("date", copyVote.getDate());
		paramMap.put("type", copyVote.getVoteType().toString());
		KeyHolder holder = new GeneratedKeyHolder();
		SqlParameterSource parameterSource = new MapSqlParameterSource(paramMap);
		int update = template.update(CREATE_VOTE, parameterSource, holder);
		if(update >0) {
			copyVote.setId((long)holder.getKey());
		}
		return copyVote;
	}

	@Override
	public Vote read(Long primaryKey) {
		SqlParameterSource parameterSource = new MapSqlParameterSource("vote_id",primaryKey);
		Vote vote = template.queryForObject(READ_VOTE, parameterSource, new VoteRowMapper());
		return vote;
	}

	@Override
	public boolean update(Vote vote) {
		boolean result = false;
		Map<String ,Object> paramMap = new HashMap<>();
		paramMap.put("date", vote.getDate());
		paramMap.put("type", vote.getVoteType().toString());
		paramMap.put("vote_id", vote.getId());
		SqlParameterSource parameterSource = new MapSqlParameterSource(paramMap);
		int update = template.update(UPDATE_VOTE,parameterSource);
		if(update >0) {
			result = true;
		}
		return result;
	}

	@Override
	public boolean delete(Long key) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Vote> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Vote getVoteByUserIdDiscoveryId(long userId, long discoveryId) {
		Map<String,Object> paramMap = new HashMap<>();
		paramMap.put("user_id", userId);
		paramMap.put("discovery_id", discoveryId);
		SqlParameterSource parameterSource = new MapSqlParameterSource(paramMap);
		Vote vote = null;
		try {
			vote = template.queryForObject(READ_VOTE_BY_DISCOVERY_USE_IDS, parameterSource,new VoteRowMapper());
		}catch(EmptyResultDataAccessException e) {
			//vote not found
		}
		
		return vote;
	}

	
	private class VoteRowMapper implements RowMapper<Vote> {

		@Override
		public Vote mapRow(ResultSet resultSet, int row) throws SQLException {
			Vote vote = new Vote();
			vote.setId(resultSet.getLong("vote_id"));
			vote.setUserId(resultSet.getLong("user_id"));
			vote.setDiscoveryId(resultSet.getLong("discovery_id"));
			vote.setDate(resultSet.getTimestamp("date"));
			vote.setVoteType(VoteType.valueOf(resultSet.getString("type")));
			return vote;
		}
		
	}
	
}
