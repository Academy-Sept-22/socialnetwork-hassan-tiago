package com.socialnetwork;

public class SocialWallService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final ClockService clockService;

    public SocialWallService(UserRepository userRepository,
                             PostRepository postRepository,
                             ClockService clockService) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.clockService = clockService;
    }

    public void post(String userName, String userPost) {
        User user = userRepository.get(userName);
        if (user == null) {
            userRepository.create(userName);
        }

        postRepository.create(userName, userPost, clockService.getCurrentTime());
    }

    public String returnPosts(String userName) {
        throw new UnsupportedOperationException();
    }
}
