# gitlab 分支整理

## 列出所有分支

运行
```shell script
java -jar gitlab-branches-tidy-1.0-SNAPSHOT.jar {projectId}
```

例如

```shell script
java -jar gitlab-branches-tidy-1.0-SNAPSHOT.jar 106
```

## 批量删除分支
```shell script
java -cp gitlab-branches-tidy-1.0-SNAPSHOT.jar com.windmt.tool.DeleteBranch {projectId} {branch1,branch2}
```

例如

```shell script
java -cp gitlab-branches-tidy-1.0-SNAPSHOT.jar com.windmt.tool.DeleteBranch 106 feature/wolf-v12,feature/wolfShowBarrageName,hideVipTitle
```

## 本地分支同步

查看本地分支和追踪情况：

```shell script
git remote show origin
```

来同步删除这些分支，运行命令：**本地有没有推到远程的分支慎用**

```shell script
git remote prune origin
```