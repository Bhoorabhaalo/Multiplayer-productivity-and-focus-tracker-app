const fs = require('fs');
const path = require('path');

function walk(dir) {
    let results = [];
    const list = fs.readdirSync(dir);
    list.forEach(file => {
        const fullPath = path.join(dir, file);
        const stat = fs.statSync(fullPath);
        if (stat && stat.isDirectory()) {
            results = results.concat(walk(fullPath));
        } else if (file.endsWith('.java')) {
            results.push(fullPath);
        }
    });
    return results;
}

const javaFiles = walk(path.join(__dirname, '../backend/src'));

let modifiedCount = 0;

javaFiles.forEach(filePath => {
    let content = fs.readFileSync(filePath, 'utf8');
    let original = content;

    // Remove lombok imports if any remain
    content = content.replace(/^import\s+lombok\..*?;\r?\n/gm, '');

    // Remove annotations at class and field levels
    content = content.replace(/@Data\r?\n/g, '');
    content = content.replace(/@Getter\r?\n/g, '');
    content = content.replace(/@Setter\r?\n/g, '');
    content = content.replace(/@NoArgsConstructor\r?\n/g, '');
    content = content.replace(/@AllArgsConstructor\r?\n/g, '');
    content = content.replace(/@Builder\.Default\r?\n/g, '');
    content = content.replace(/@Builder\r?\n/g, '');
    content = content.replace(/@ToString\r?\n/g, '');
    content = content.replace(/@EqualsAndHashCode.*?\r?\n/g, '');

    if (content !== original) {
        fs.writeFileSync(filePath, content, 'utf8');
        modifiedCount++;
    }
});

console.log(`Cleaned leftover lombok annotations from ${modifiedCount} files.`);
