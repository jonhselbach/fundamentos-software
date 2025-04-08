import java.util.*;
import java.util.stream.Collectors;

class Video {
    String title;
    String category;
    int views;

    public Video(String title, String category, int views) {
        this.title = title;
        this.category = category;
        this.views = views;
    }

    @Override
    public String toString() {
        return title + " (" + category + ") - " + views + " views";
    }
}

class User {
    String name;
    String preferredCategory;
    Set<String> watchedVideos;

    public User(String name, String preferredCategory) {
        this.name = name;
        this.preferredCategory = preferredCategory;
        this.watchedVideos = new HashSet<>();
    }

    public void watch(Video video) {
        watchedVideos.add(video.title);
    }

    public boolean hasWatched(Video video) {
        return watchedVideos.contains(video.title);
    }
}

class RecommendationEngine {
    public List<Video> recommendVideos(User user, List<Video> videos) {
        return videos.stream()
                .filter(video -> !user.hasWatched(video)) // vídeos ainda não assistidos
                .sorted((v1, v2) -> {
                    // prioriza vídeos da categoria preferida com mais visualizações
                    int score1 = (v1.category.equals(user.preferredCategory) ? 1000 : 0) + v1.views;
                    int score2 = (v2.category.equals(user.preferredCategory) ? 1000 : 0) + v2.views;
                    return Integer.compare(score2, score1); // ordem decrescente
                })
                .limit(5)
                .collect(Collectors.toList());
    }
}

public class Main {
    public static void main(String[] args) {
        List<Video> videos = Arrays.asList(
                new Video("Como fazer café", "Culinária", 5000),
                new Video("Aprenda Java em 10 minutos", "Educação", 2500),
                new Video("Melhores gols de 2024", "Esportes", 3000),
                new Video("Documentário sobre o espaço", "Educação", 1200),
                new Video("Receita de pão caseiro", "Culinária", 1800),
                new Video("Compilado de fails engraçados", "Humor", 4000)
        );

        User user = new User("João", "Educação");
        user.watch(videos.get(1));

        RecommendationEngine engine = new RecommendationEngine();
        List<Video> recommendations = engine.recommendVideos(user, videos);

        System.out.println("Recomendações para " + user.name + ":");
        recommendations.forEach(System.out::println);
    }
}