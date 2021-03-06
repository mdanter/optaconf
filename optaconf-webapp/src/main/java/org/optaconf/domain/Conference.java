package org.optaconf.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.optaplanner.core.api.domain.solution.PlanningEntityCollectionProperty;
import org.optaplanner.core.api.domain.solution.PlanningSolution;
import org.optaplanner.core.api.domain.solution.Solution;
import org.optaplanner.core.api.domain.valuerange.ValueRangeProvider;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;

@PlanningSolution
@Entity(name = "optaconf_conference")
public class Conference extends AbstractPersistable implements Solution<HardSoftScore> {

    @NotNull @Size(max = 120)
    private String name;
    @NotNull @Size(max = 240)
    private String comment;

    @OneToMany(mappedBy = "conference", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<Day> dayList = new ArrayList<Day>();

    @ValueRangeProvider(id = "timeslotRange")
    @OneToMany(mappedBy = "conference", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<Timeslot> timeslotList = new ArrayList<Timeslot>();

    @ValueRangeProvider(id = "roomRange")
    @OneToMany(mappedBy = "conference", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<Room> roomList = new ArrayList<Room>();

    @OneToMany(mappedBy = "conference", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<UnavailableTimeslotRoomPenalty> unavailableTimeslotRoomPenaltyList = new ArrayList<UnavailableTimeslotRoomPenalty>();

    @OneToMany(mappedBy = "conference", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<Track> trackList = new ArrayList<Track>();

    @OneToMany(mappedBy = "conference", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<Speaker> speakerList = new ArrayList<Speaker>();

    @PlanningEntityCollectionProperty
    @OneToMany(mappedBy = "conference", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<Talk> talkList = new ArrayList<Talk>();

    @OneToMany(mappedBy = "conference", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<SpeakingRelation> speakingRelationList = new ArrayList<SpeakingRelation>();

    @OneToMany(mappedBy = "conference", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<TalkExclusion> talkExclusionList = new ArrayList<TalkExclusion>();

    // TODO Replace database blob with nice columns - https://issues.jboss.org/browse/PLANNER-442
    private HardSoftScore score;

    public Conference() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public List<Day> getDayList() {
        return dayList;
    }

    public void setDayList(List<Day> dayList) {
        this.dayList = dayList;
    }

    public List<Timeslot> getTimeslotList() {
        return timeslotList;
    }

    public void setTimeslotList(List<Timeslot> timeslotList) {
        this.timeslotList = timeslotList;
    }

    public List<Room> getRoomList() {
        return roomList;
    }

    public void setRoomList(List<Room> roomList) {
        this.roomList = roomList;
    }

    public List<UnavailableTimeslotRoomPenalty> getUnavailableTimeslotRoomPenaltyList() {
        return unavailableTimeslotRoomPenaltyList;
    }

    public void setUnavailableTimeslotRoomPenaltyList(
            List<UnavailableTimeslotRoomPenalty> unavailableTimeslotRoomPenaltyList) {
        this.unavailableTimeslotRoomPenaltyList = unavailableTimeslotRoomPenaltyList;
    }

    public List<Track> getTrackList() {
        return trackList;
    }

    public void setTrackList(List<Track> trackList) {
        this.trackList = trackList;
    }

    public List<Speaker> getSpeakerList() {
        return speakerList;
    }

    public void setSpeakerList(List<Speaker> speakerList) {
        this.speakerList = speakerList;
    }

    public List<Talk> getTalkList() {
        return talkList;
    }

    public void setTalkList(List<Talk> talkList) {
        this.talkList = talkList;
    }

    public List<TalkExclusion> getTalkExclusionList() {
        return talkExclusionList;
    }

    public List<SpeakingRelation> getSpeakingRelationList() {
        return speakingRelationList;
    }

    public void setSpeakingRelationList(List<SpeakingRelation> speakingRelationList) {
        this.speakingRelationList = speakingRelationList;
    }

    public void setTalkExclusionList(List<TalkExclusion> talkExclusionList) {
        this.talkExclusionList = talkExclusionList;
    }

    public HardSoftScore getScore() {
        return score;
    }

    public void setScore(HardSoftScore score) {
        this.score = score;
    }

    // ************************************************************************
    // Real methods
    // ************************************************************************

    @Override
    @JsonIgnore
    public Collection<?> getProblemFacts() {
        List<Object> facts = new ArrayList<Object>();
        facts.addAll(dayList);
        facts.addAll(timeslotList);
        facts.addAll(roomList);
        facts.addAll(unavailableTimeslotRoomPenaltyList);
        facts.addAll(trackList);
        facts.addAll(speakerList);
        facts.addAll(speakingRelationList);
        facts.addAll(talkList);
        facts.addAll(talkExclusionList);
        // Do not add the planning entity's (processList) because that will be done automatically
        return facts;
    }

}
