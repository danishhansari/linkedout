package com.linkedout.postservice.repository;

import com.linkedout.postservice.entity.Like;
import com.linkedout.postservice.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LikeRepository extends JpaRepository<Like, UUID> {
}
