
import com.toedter.calendar.JCalendar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class LectureScheduleUI extends JFrame {
    private final JCalendar calendar;
    private final JTextArea scheduleArea;
    private final JButton addScheduleBtn;
    private final JButton viewSchedulesBtn;

    // 날짜별 일정 목록
    private final Map<Date, java.util.List<String>> schedules = new HashMap<>();

    public LectureScheduleUI() {
        setTitle("강의 일정 캘린더");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 달력
        calendar = new JCalendar();

        // 일정 표시 영역
        scheduleArea = new JTextArea(10, 40);
        scheduleArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(scheduleArea);

        // 버튼
        addScheduleBtn = new JButton("일정 추가");
        viewSchedulesBtn = new JButton("일정 보기");

        // 버튼 동작
        addScheduleBtn.addActionListener(e -> addSchedule());
        viewSchedulesBtn.addActionListener(e -> viewSchedules());

        // 패널 구성
        JPanel btnPanel = new JPanel();
        btnPanel.add(addScheduleBtn);
        btnPanel.add(viewSchedulesBtn);

        // 레이아웃
        add(calendar, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(btnPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    // 일정 추가
    private void addSchedule() {
        Date selectedDate = calendar.getDate();
        String schedule = JOptionPane.showInputDialog(this, "일정 입력:");

        if (schedule != null && !schedule.trim().isEmpty()) {
            schedules.computeIfAbsent(selectedDate, k -> new ArrayList<>()).add(schedule.trim());
            JOptionPane.showMessageDialog(this, "일정 추가 완료!");
        }
    }

    // 일정 보기
    private void viewSchedules() {
        Date selectedDate = calendar.getDate();
        java.util.List<String> daySchedules = schedules.getOrDefault(selectedDate, new ArrayList<>());

        if (daySchedules.isEmpty()) {
            scheduleArea.setText("일정이 없습니다.");
        } else {
            StringBuilder sb = new StringBuilder();
            for (String s : daySchedules) {
                sb.append("- ").append(s).append("\n");
            }
            scheduleArea.setText(sb.toString());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(LectureScheduleUI::new);
    }
}
