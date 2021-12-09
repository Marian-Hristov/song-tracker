package dawson.songtracker.front;

import dawson.songtracker.back.types.DatabaseObject;
import dawson.songtracker.back.types.components.Compilation;
import dawson.songtracker.back.types.components.Recording;
import dawson.songtracker.back.types.components.Segment;
import dawson.songtracker.back.types.distributions.Collection;
import dawson.songtracker.back.types.distributions.Distribution;
import dawson.songtracker.back.types.distributions.Market;
import dawson.songtracker.back.types.distributions.RecordLabel;
import dawson.songtracker.back.types.roles.Contributor;
import dawson.songtracker.back.types.roles.Role;
import dawson.songtracker.front.Cache;

import java.util.HashMap;
import java.util.Map;

public class CacheManager {
    private static Cache<Compilation> compilations = new Cache<>();
    private static Cache<Recording> recordings = new Cache<>();
    private static Cache<Segment> segments = new Cache<>();
    private static Cache<Collection> collections = new Cache<>();
    private static Cache<Distribution> distributions = new Cache<>();
    private static Cache<Market> markets = new Cache<>();
    private static Cache<RecordLabel> recordLabels = new Cache<>();
    private static Cache<Role> roles = new Cache<>();
    private static Cache<Contributor> contributors = new Cache<>();

    private static Map<Class, Cache> map = new HashMap<>();
    static {
        map.put(Compilation.class, compilations);
        map.put(Recording.class, recordings);
        map.put(Segment.class, segments);
        map.put(Collection.class, collections);
        map.put(Distribution.class, distributions);
        map.put(Market.class, markets);
        map.put(RecordLabel.class, recordLabels);
        map.put(Role.class, roles);
        map.put(Contributor.class, contributors);
    }

    public static <T extends DatabaseObject> Cache<T> getCache(Class c) {
        return map.get(c);
    }

    public static Cache<Compilation> getCompilations() {
        return compilations;
    }

    public static Cache<Recording> getRecordings() {
        return recordings;
    }

    public static Cache<Segment> getSegments() {
        return segments;
    }

    public static Cache<Collection> getCollections() {
        return collections;
    }

    public static Cache<Distribution> getDistributions() {
        return distributions;
    }

    public static Cache<Market> getMarkets() {
        return markets;
    }

    public static Cache<RecordLabel> getRecordLabels() {
        return recordLabels;
    }

    public static Cache<Role> getRoles() {
        return roles;
    }

    public static Cache<Contributor> getContributors() {
        return contributors;
    }
}
