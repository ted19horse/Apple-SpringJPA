package ted.applespringjpa;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class Notice {
  @Id
  @GeneratedValue(strategy= GenerationType.IDENTITY)
  public Long id;
  public String 글제목;
  public LocalDate 날짜;
}
