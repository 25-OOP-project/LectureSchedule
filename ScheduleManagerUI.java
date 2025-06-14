// ScheduleManagerUI.java
import com.toedter.calendar.JCalendar;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class ScheduleManagerUI extends JFrame {
    private final JCalendar calendar;
    private final JTextArea scheduleArea;
    private final JButton addScheduleBtn;
    private final JButton viewSchedulesBtn;

    private final ScheduleManager scheduleManager = new ScheduleManager();
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    private final LectureManager lectureManager;
    private final AttendanceManager attendanceManager;

    public ScheduleManagerUI(LectureManager lectureManager, AttendanceManager attendanceManager) {
        this.lectureManager = lectureManager;
        this.attendanceManager = attendanceManager;

        setTitle("강의 일정 캘린더");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // FileIO로부터 일정 데이터 불러오기
        scheduleManager.setScheduleMap(FileIO.loadSchedule());

        calendar = new JCalendar();
        scheduleArea = new JTextArea(10, 40);
        scheduleArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(scheduleArea);

        addScheduleBtn = new JButton("일정 추가");
        viewSchedulesBtn = new JButton("일정 보기");

        addScheduleBtn.addActionListener(e -> addSchedule());
        viewSchedulesBtn.addActionListener(e -> viewSchedules());

        JPanel btnPanel = new JPanel();
        btnPanel.add(addScheduleBtn);
        btnPanel.add(viewSchedulesBtn);

        add(calendar, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(btnPanel, BorderLayout.SOUTH);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                FileIO.saveSchedule(scheduleManager.getScheduleMap());
            }
        });

        setVisible(true);
    }

    private void addSchedule() {
        Date selectedDate = calendar.getDate();
        String dateStr = dateFormat.format(selectedDate);
        String schedule = JOptionPane.showInputDialog(this, "일정 입력:");

        if (schedule != null && !schedule.trim().isEmpty()) {
            scheduleManager.addSchedule(dateStr, schedule.trim());
            JOptionPane.showMessageDialog(this, "일정 추가 완료!");
        }
    }

    private void viewSchedules() {
        Date selectedDate = calendar.getDate();
        String dateStr = dateFormat.format(selectedDate);
        List<String> daySchedules = scheduleManager.getSchedulesForDate(dateStr);

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
        LectureManager lectureManager = new LectureManager();
        AttendanceManager attendanceManager = new AttendanceManager(lectureManager);
        SwingUtilities.invokeLater(() -> new ScheduleManagerUI(lectureManager, attendanceManager));
    }
}
