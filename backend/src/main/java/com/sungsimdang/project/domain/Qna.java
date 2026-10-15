package com.sungsimdang.project.domain;

import com.sungsimdang.project.member.domain.Member;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "qna")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Qna {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(nullable = false, name = "member_id")
    private Member member;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(columnDefinition = "TEXT")
    private String answer;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private QnaStatus status = QnaStatus.WAITING;

    @Column(nullable = false, updatable = false, name = "created_at")
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    @Builder.Default
    private LocalDateTime updatedAt = LocalDateTime.now();

    @Column(name = "answered_at")
    private LocalDateTime answeredAt;

    @Column(nullable = false)
    @Builder.Default
    private long hits = 0L;

    @PrePersist
    public void prePersist() {
        if(createdAt == null) {
            createdAt = LocalDateTime.now();
        }
        if(status == null) {
            status = QnaStatus.WAITING;
        }
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public void updateQuestion(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void writeAnswer(String answer) {
        this.answer = answer;
        this.status = QnaStatus.ANSWERED;
        this.answeredAt = LocalDateTime.now();
    }

    public void deleteAnswer() {
        this.answer = null;
        this.status = QnaStatus.WAITING;
        this.answeredAt = null;
    }
}
