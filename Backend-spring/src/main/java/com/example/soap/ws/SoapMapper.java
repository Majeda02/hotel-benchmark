package com.example.soap.ws;

import com.example.soap.domain.ReservationEntity;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.GregorianCalendar;

public final class SoapMapper {
  private SoapMapper() {}

  public static Reservation toSoap(ReservationEntity e) {
    Reservation r = new Reservation();
    r.setId(e.getId());

    Client c = new Client();
    c.setId(e.getClient().getId());
    c.setNom(e.getClient().getNom());
    c.setPrenom(e.getClient().getPrenom());
    c.setEmail(e.getClient().getEmail());
    c.setTelephone(e.getClient().getTelephone());
    r.setClient(c);

    Chambre ch = new Chambre();
    ch.setId(e.getChambre().getId());
    ch.setType(e.getChambre().getType());
    ch.setPrix(e.getChambre().getPrix().toPlainString());
    ch.setDisponible(Boolean.TRUE.equals(e.getChambre().getDisponible()));
    r.setChambre(ch);

    r.setDateDebut(toXmlDate(e.getDateDebut().getYear(), e.getDateDebut().getMonthValue(), e.getDateDebut().getDayOfMonth()));
    r.setDateFin(toXmlDate(e.getDateFin().getYear(), e.getDateFin().getMonthValue(), e.getDateFin().getDayOfMonth()));
    r.setPreferences(e.getPreferences());
    return r;
  }

  private static XMLGregorianCalendar toXmlDate(int y, int m, int d) {
    try {
      GregorianCalendar cal = new GregorianCalendar();
      cal.clear();
      cal.set(y, m - 1, d);
      return DatatypeFactory.newInstance().newXMLGregorianCalendarDate(y, m, d, cal.getTimeZone().getOffset(cal.getTimeInMillis())/60000);
    } catch (Exception ex) {
      throw new RuntimeException(ex);
    }
  }
}
