package dawson.songtracker.front.controllers.detail;

import dawson.songtracker.back.types.roles.Contributor;
import dawson.songtracker.back.types.roles.Role;

public record ContributorRole(Contributor contributor, Role role){};
