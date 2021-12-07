package dinhhieumvc.dao;

import dinhhieumvc.entity.Relationship;

public interface RelationshipDAO extends GenericDAO<Relationship, Integer> {
	public boolean isFollowing(Relationship relationship);
	public Relationship load(Relationship relationship);
}
