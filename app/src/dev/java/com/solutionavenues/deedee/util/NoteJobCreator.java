package com.solutionavenues.deedee.util;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobCreator;

public class NoteJobCreator implements JobCreator {

    @Override
    @Nullable
    public Job create(@NonNull String tag) {
        switch (tag) {
            case NoteSyncJob.TAG:
                return new NoteSyncJob();
            default:
                return null;
        }
    }
}
