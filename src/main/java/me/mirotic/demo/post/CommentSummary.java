package me.mirotic.demo.post;

public interface CommentSummary {

    int getUp();

    int getDown();

    default int getTotalCount() {
        return getUp() + getDown();
    }
}
