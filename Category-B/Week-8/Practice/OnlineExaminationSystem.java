import java.util.*;

class Answer {
    private final Question question; private final String value;
    Answer(Question q, String v) { question = q; value = v; }
    public Question getQuestion() { return question; }
    public String getValue() { return value; }
}

// ---------- Each question type evaluates itself ----------
abstract class Question {
    private final int number; private final String text; private final int points;
    protected Question(int n, String t, int p) { number = n; text = t; points = p; }
    public int getNumber() { return number; }
    public int getPoints() { return points; }
    public abstract boolean isCorrect(String answer);
    public int score(Answer a) { return (a != null && isCorrect(a.getValue())) ? points : 0; }
}
class MultipleChoiceQuestion extends Question {
    private final String correctOption;
    MultipleChoiceQuestion(int n, String t, int p, String correct) { super(n, t, p); correctOption = correct; }
    public boolean isCorrect(String a) { return correctOption.equalsIgnoreCase(a.trim()); }
}
class TrueFalseQuestion extends Question {
    private final boolean correct;
    TrueFalseQuestion(int n, String t, int p, boolean c) { super(n, t, p); correct = c; }
    public boolean isCorrect(String a) { return Boolean.parseBoolean(a.trim()) == correct && (a.equalsIgnoreCase("true") || a.equalsIgnoreCase("false")); }
}
class ShortAnswerQuestion extends Question {
    private final List<String> keywords;
    ShortAnswerQuestion(int n, String t, int p, String... kw) { super(n, t, p); keywords = Arrays.asList(kw); }
    public boolean isCorrect(String a) {
        String l = a.toLowerCase();
        return keywords.stream().allMatch(k -> l.contains(k.toLowerCase()));
    }
}

class Student {
    private final int id;
    Student(int id) { this.id = id; }
    public int getId() { return id; }
}

class Examination {
    private final String title; private final List<Question> questions = new ArrayList<>();
    Examination(String t) { title = t; }
    public String getTitle() { return title; }
    public void addQuestion(Question q) { questions.add(q); }
    public List<Question> getQuestions() { return Collections.unmodifiableList(questions); }
    public int totalPoints() { return questions.stream().mapToInt(Question::getPoints).sum(); }
}

class Attempt {
    enum State { IN_PROGRESS, SUBMITTED }
    private final Student student; private final Examination exam;
    private final Map<Question, Answer> answers = new LinkedHashMap<>();
    private State state = State.IN_PROGRESS;
    Attempt(Student s, Examination e) { student = s; exam = e; }
    public Student getStudent() { return student; }
    public Examination getExam() { return exam; }
    public boolean isSubmitted() { return state == State.SUBMITTED; }

    public boolean answer(Question q, String value) {
        if (isSubmitted()) { System.out.println("Cannot change answers for a submitted examination."); return false; }
        answers.put(q, new Answer(q, value));
        System.out.println("Answer recorded for Question " + q.getNumber() + ".");
        return true;
    }
    void submit() { state = State.SUBMITTED; }

    public void printResult() {
        int total = 0; StringBuilder sb = new StringBuilder("Result: ");
        for (Question q : exam.getQuestions()) {
            int s = q.score(answers.get(q)); total += s;
            sb.append("Question ").append(q.getNumber()).append(": ")
              .append(s > 0 ? "Correct" : "Incorrect").append(" (").append(s).append(" points), ");
        }
        System.out.println(sb + "Total score: " + total + "/" + exam.totalPoints() + ".");
    }
}

class ExamService {
    private final Map<String, Attempt> attempts = new HashMap<>(); // key: studentId|exam
    public Attempt start(Student s, Examination e) {
        String key = s.getId() + "|" + e.getTitle();
        Attempt existing = attempts.get(key);
        if (existing != null && existing.isSubmitted()) {
            System.out.println("Student " + s.getId() + " has already submitted " + e.getTitle() + ".");
            return null;
        }
        Attempt a = new Attempt(s, e);
        attempts.put(key, a);
        System.out.println(e.getTitle() + " started by Student " + s.getId() + ".");
        return a;
    }
    public void submit(Attempt a) {
        a.submit();
        System.out.println(a.getExam().getTitle() + " submitted by Student " + a.getStudent().getId() + ".");
        a.printResult();   // works for any Question subtype
    }
}

public class OnlineExaminationSystem {
    public static void main(String[] args) {
        Examination examA = new Examination("Exam A");
        Question q1 = new MultipleChoiceQuestion(1, "Pick one", 5, "C");
        Question q2 = new TrueFalseQuestion(2, "Java is compiled only", 5, false);
        examA.addQuestion(q1); examA.addQuestion(q2);

        ExamService svc = new ExamService();
        Student s1 = new Student(1);
        Attempt at = svc.start(s1, examA);
        at.answer(q1, "C");
        at.answer(q2, "true");
        svc.submit(at);
        at.answer(q1, "A");
    }
}
