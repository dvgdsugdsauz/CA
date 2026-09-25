import { Injectable, inject } from '@angular/core';
import { Observable } from 'rxjs';
import { ApiClient } from '../http/api-client';
import { API } from '../http/api-endpoints';
import { CommandResult } from '../http/api-response';
import { TeamMember, TeamMemberRequest } from '../models';

/** The firm's people (TeamMemberController). */
@Injectable({ providedIn: 'root' })
export class TeamMemberService {
  private readonly api = inject(ApiClient);

  // ---------------- Public ----------------

  /** Active team members, optionally only those at one office. */
  getActive(locationSlug?: string): Observable<TeamMember[]> {
    return this.api.getList<TeamMember>(API.TeamMember.GET_ACTIVE_TEAM_MEMBERS, { locationSlug });
  }

  // ---------------- Back office ----------------

  getAll(): Observable<TeamMember[]> {
    return this.api.getList<TeamMember>(API.TeamMember.GET_ALL_TEAM_MEMBERS);
  }

  save(request: TeamMemberRequest): Observable<CommandResult> {
    return this.api.command(API.TeamMember.SAVE_TEAM_MEMBER, request);
  }

  delete(teamMemberId: number): Observable<CommandResult> {
    return this.api.command(API.TeamMember.DELETE_TEAM_MEMBER, null, { teamMemberId });
  }
}
