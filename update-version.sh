#!/bin/bash

# קריאת גרסה נוכחית מ-pom.xml
CURRENT_VERSION=$(grep -oP '(?<=<version>).*?(?=</version>)' pom.xml | head -1)

# בדיקה האם הגרסה כוללת SNAPSHOT
if [[ "$CURRENT_VERSION" == *"-SNAPSHOT" ]]; then
  BASE_VERSION=${CURRENT_VERSION%-SNAPSHOT}
  IS_SNAPSHOT=true
else
  BASE_VERSION=$CURRENT_VERSION
  IS_SNAPSHOT=false
fi

# הדפסת מידע לדיבוג
echo "Current Version: $CURRENT_VERSION"
echo "Base Version: $BASE_VERSION"
echo "Is Snapshot: $IS_SNAPSHOT"

# פרוק הגרסה לשלושת הרכיבים (Major.Minor.Patch)
IFS='.' read -r MAJOR MINOR PATCH <<< "$BASE_VERSION"
NEW_PATCH=$((PATCH + 1))

# בניית גרסה חדשה
if [ "$IS_SNAPSHOT" = true ]; then
  NEW_VERSION="${MAJOR}.${MINOR}.${NEW_PATCH}-SNAPSHOT"
else
  NEW_VERSION="${MAJOR}.${MINOR}.${NEW_PATCH}"
fi

# עדכון גרסה ב-pom.xml
sed -i "s|<version>${CURRENT_VERSION}</version>|<version>${NEW_VERSION}</version>|" pom.xml

# הדפסת הודעה על עדכון
echo "Updated version to ${NEW_VERSION}"
