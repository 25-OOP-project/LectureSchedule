import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScheduleManager {
    private Map<String, List<String>> scheduleMap = new HashMap<>();

    public void setScheduleMap(Map<String, List<String>> map) {
        this.scheduleMap = map;
    }

    public Map<String, List<String>> getScheduleMap() {
        return scheduleMap;
    }

    public void addSchedule(String date, String content) {
        scheduleMap.computeIfAbsent(date, k -> new ArrayList<>()).add(content);
    }

    public List<String> getSchedulesForDate(String date) {
        return scheduleMap.getOrDefault(date, new ArrayList<>());
    }
}
