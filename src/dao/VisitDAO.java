package dao;

import model.Visit;

import java.util.List;
public interface VisitDAO {
    void addVisit(Visit visit);
    void updateVisit(Visit visit);
    void deleteVisit(int id);
    List<Visit> getAllVisits();
}
